package com.example.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.admin.DeviceAdminHelper
import com.example.admin.RenderAdminManager
import com.example.curfew.CurfewAlarmScheduler
import com.example.curfew.CurfewConfig
import com.example.curfew.CurfewLockActivity
import com.example.curfew.CurfewManager
import com.example.data.local.SafeGuardDatabase
import com.example.data.local.SecurityLogEntity
import com.example.data.model.PhoneSecurityStats
import com.example.islamic.AudioMode
import com.example.islamic.IslamicContentRepository
import com.example.islamic.QuranAudioManager
import com.example.islamic.QuranSurah
import com.example.security.SecurityManager
import com.example.util.AppLanguage
import com.example.util.LanguageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class SafeGuardTab {
    OVERVIEW,
    BEDTIME_LOCK,
    ISLAMIC_RECOVERY,
    ANTI_UNINSTALL,
    RENDER_ADMIN,
    ACTIVITY_LOGS,
    SETTINGS
}

sealed class PinAction {
    object DeactivateShield : PinAction()
    object DeactivateAdmin : PinAction()
    object ChangePin : PinAction()
    object EmergencyCurfewOverride : PinAction()
    object ClearActivityLogs : PinAction()
}

class SafeGuardViewModel(application: Application) : AndroidViewModel(application) {
    private val context: Context = application.applicationContext
    private val securityManager = SecurityManager(context)
    private val curfewManager = CurfewManager(context)
    val renderAdminManager = RenderAdminManager(context)
    private val database = SafeGuardDatabase.getDatabase(context)
    val quranAudioManager = QuranAudioManager(context)
    private val languageManager = LanguageManager(context)

    // Current Navigation Tab
    private val _currentTab = MutableStateFlow(SafeGuardTab.OVERVIEW)
    val currentTab: StateFlow<SafeGuardTab> = _currentTab.asStateFlow()

    // Master Shield State
    private val _isShieldActive = MutableStateFlow(securityManager.isShieldEnabled())
    val isShieldActive: StateFlow<Boolean> = _isShieldActive.asStateFlow()

    // Device Admin Status
    private val _isDeviceAdminActive = MutableStateFlow(DeviceAdminHelper.isAdminActive(context))
    val isDeviceAdminActive: StateFlow<Boolean> = _isDeviceAdminActive.asStateFlow()

    // PIN Security States
    private val _isPinConfigured = MutableStateFlow(securityManager.isPinConfigured())
    val isPinConfigured: StateFlow<Boolean> = _isPinConfigured.asStateFlow()

    private val _pendingPinAction = MutableStateFlow<PinAction?>(null)
    val pendingPinAction: StateFlow<PinAction?> = _pendingPinAction.asStateFlow()

    private val _showSetupPinDialog = MutableStateFlow(!securityManager.isPinConfigured())
    val showSetupPinDialog: StateFlow<Boolean> = _showSetupPinDialog.asStateFlow()

    // Curfew Lock State
    private val _curfewConfig = MutableStateFlow(curfewManager.getCurfewConfig())
    val curfewConfig: StateFlow<CurfewConfig> = _curfewConfig.asStateFlow()

    private val _isCurfewLockActive = MutableStateFlow(curfewManager.isCurfewCurrentlyActive())
    val isCurfewLockActive: StateFlow<Boolean> = _isCurfewLockActive.asStateFlow()

    // Activity Logs from Room DB
    val recentLogs: StateFlow<List<SecurityLogEntity>> = database.securityLogDao()
        .getRecentLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Security Stats
    private val _securityStats = MutableStateFlow(
        PhoneSecurityStats(
            curfewLocksEnforced = 14,
            tamperAttemptsBlocked = 3,
            pinVerifications = 8,
            recoveryDaysStreak = securityManager.getRecoveryStreak()
        )
    )
    val securityStats: StateFlow<PhoneSecurityStats> = _securityStats.asStateFlow()

    // Quran Recitations & Islamic Guide
    val surahList: List<QuranSurah> = IslamicContentRepository.getRecommendedSurahs()
    private val _selectedAudioMode = MutableStateFlow(AudioMode.WITH_BANGLA_TRANSLATION)
    val selectedAudioMode: StateFlow<AudioMode> = _selectedAudioMode.asStateFlow()

    // Notification / Toast message
    private val _statusBanner = MutableStateFlow<String?>(null)
    val statusBanner: StateFlow<String?> = _statusBanner.asStateFlow()

    // Language State
    private val _appLanguage = MutableStateFlow(languageManager.getLanguage())
    val appLanguage: StateFlow<AppLanguage> = _appLanguage.asStateFlow()

    init {
        refreshState()
    }

    fun selectTab(tab: SafeGuardTab) {
        _currentTab.value = tab
    }

    fun clearStatusBanner() {
        _statusBanner.value = null
    }

    fun refreshState() {
        _isDeviceAdminActive.value = DeviceAdminHelper.isAdminActive(context)
        _isPinConfigured.value = securityManager.isPinConfigured()
        _isCurfewLockActive.value = curfewManager.isCurfewCurrentlyActive()
        _curfewConfig.value = curfewManager.getCurfewConfig()
        _appLanguage.value = languageManager.getLanguage()
        _securityStats.value = _securityStats.value.copy(
            recoveryDaysStreak = securityManager.getRecoveryStreak()
        )
    }

    // Toggle Master Shield
    fun toggleMasterShield() {
        if (_isShieldActive.value) {
            if (_isPinConfigured.value) {
                _pendingPinAction.value = PinAction.DeactivateShield
            } else {
                executeDeactivateShield()
            }
        } else {
            executeActivateShield()
        }
    }

    private fun executeActivateShield() {
        securityManager.setShieldEnabled(true)
        _isShieldActive.value = true
        _statusBanner.value = "Safe Guard Master Shield Activated"
        logSecurityEvent("SYSTEM", "Master Shield Activated", "Full protection is active", "SUCCESS")
    }

    private fun executeDeactivateShield() {
        securityManager.setShieldEnabled(false)
        _isShieldActive.value = false
        _statusBanner.value = "Safe Guard Master Shield Deactivated"
        logSecurityEvent("SYSTEM", "Master Shield Deactivated", "Protection paused by user", "WARNING")
    }

    // Device Admin Actions
    fun requestEnableDeviceAdmin(activity: android.app.Activity) {
        DeviceAdminHelper.requestDeviceAdmin(activity)
    }

    fun requestDeactivateAdmin() {
        if (_isPinConfigured.value) {
            _pendingPinAction.value = PinAction.DeactivateAdmin
        } else {
            executeDeactivateAdmin()
        }
    }

    private fun executeDeactivateAdmin() {
        DeviceAdminHelper.removeDeviceAdmin(context)
        _isDeviceAdminActive.value = false
        _statusBanner.value = "Device Admin Protection Removed"
        logSecurityEvent("DEVICE_ADMIN", "Admin Removed", "Tamper resistance disabled", "ALERT")
    }

    // PIN Authentication
    fun verifyPin(pin: String): Boolean {
        return securityManager.verifyPin(pin)
    }

    fun onPinSuccess() {
        val action = _pendingPinAction.value
        _pendingPinAction.value = null
        when (action) {
            is PinAction.DeactivateShield -> executeDeactivateShield()
            is PinAction.DeactivateAdmin -> executeDeactivateAdmin()
            is PinAction.ClearActivityLogs -> clearLogsInternal()
            is PinAction.ChangePin -> {
                _showSetupPinDialog.value = true
            }
            is PinAction.EmergencyCurfewOverride -> {
                curfewManager.grantEmergencyUnlock(15)
                _isCurfewLockActive.value = false
                _statusBanner.value = "১৫ মিনিটের জন্য জরুরি অ্যাক্সেস দেওয়া হয়েছে"
                logSecurityEvent("CURFEW_LOCK", "Emergency Override", "Temporary 15 min unlock granted", "WARNING")
            }
            null -> {}
        }
    }

    fun dismissPinDialog() {
        _pendingPinAction.value = null
    }

    // Setup Master PIN
    fun setupMasterPin(pin: String, question: String, answer: String): Boolean {
        val success = securityManager.setMasterPin(pin, question, answer)
        if (success) {
            _isPinConfigured.value = true
            _showSetupPinDialog.value = false
            _statusBanner.value = "Administrator Master PIN configured securely"
            logSecurityEvent("PIN_SECURITY", "PIN Updated", "Administrator PIN changed", "INFO")
        }
        return success
    }

    fun dismissSetupPinDialog() {
        if (_isPinConfigured.value) {
            _showSetupPinDialog.value = false
        }
    }

    fun requestChangePin() {
        if (_isPinConfigured.value) {
            _pendingPinAction.value = PinAction.ChangePin
        } else {
            _showSetupPinDialog.value = true
        }
    }

    fun requestClearLogs() {
        if (_isPinConfigured.value) {
            _pendingPinAction.value = PinAction.ClearActivityLogs
        } else {
            clearLogsInternal()
        }
    }

    private fun clearLogsInternal() {
        viewModelScope.launch(Dispatchers.IO) {
            database.securityLogDao().clearAll()
            _statusBanner.value = "Activity logs cleared"
        }
    }

    fun getSecurityQuestion(): String {
        return securityManager.getSecurityQuestion()
    }

    fun verifySecurityAnswer(answer: String): Boolean {
        return securityManager.verifySecurityAnswer(answer)
    }

    fun isLockedOut(): Boolean {
        return securityManager.isLockedOut()
    }

    fun getRemainingLockoutSeconds(): Long {
        return securityManager.getRemainingLockoutSeconds()
    }

    // Curfew Bedtime Methods
    fun updateCurfewConfig(config: CurfewConfig) {
        curfewManager.saveCurfewConfig(config)
        _curfewConfig.value = config
        val isActive = curfewManager.isCurfewCurrentlyActive()
        _isCurfewLockActive.value = isActive
        if (config.isEnabled) {
            CurfewAlarmScheduler.scheduleCurfewAlarms(context, config)
            if (isActive) {
                CurfewLockActivity.startLock(context)
                DeviceAdminHelper.lockDeviceNow(context)
            }
            logSecurityEvent(
                "CURFEW_LOCK",
                "Curfew Scheduled",
                "Phone lock scheduled from ${config.startHour}:${config.startMinute} to ${config.endHour}:${config.endMinute}",
                "INFO"
            )
        } else {
            CurfewAlarmScheduler.cancelCurfewAlarms(context)
            logSecurityEvent("CURFEW_LOCK", "Curfew Disabled", "Phone lock schedule disabled", "INFO")
        }
        _statusBanner.value = if (config.isEnabled) "পুরো ফোন কার্ফিউ লক সক্রিয় করা হয়েছে" else "কার্ফিউ লক বন্ধ করা হয়েছে"
    }

    fun requestEmergencyCurfewOverride() {
        if (_isPinConfigured.value) {
            _pendingPinAction.value = PinAction.EmergencyCurfewOverride
        } else {
            curfewManager.grantEmergencyUnlock(15)
            _isCurfewLockActive.value = false
        }
    }

    fun lockNowUntil(hour: Int, minute: Int, label: String) {
        curfewManager.startImmediateLockUntil(hour, minute, label)
        _curfewConfig.value = curfewManager.getCurfewConfig()
        _isCurfewLockActive.value = true
        CurfewLockActivity.startLock(context)
        DeviceAdminHelper.lockDeviceNow(context)
        logSecurityEvent("IMMEDIATE_LOCK", "Immediate Phone Lock", label, "WARNING")
        _statusBanner.value = "ফোন লক সক্রিয় হয়েছে ($label)"
    }

    fun lockNowForMinutes(minutes: Int, label: String) {
        curfewManager.startImmediateLockForMinutes(minutes, label)
        _curfewConfig.value = curfewManager.getCurfewConfig()
        _isCurfewLockActive.value = true
        CurfewLockActivity.startLock(context)
        DeviceAdminHelper.lockDeviceNow(context)
        logSecurityEvent("IMMEDIATE_LOCK", "Immediate Phone Lock", "$minutes মিনিটের জন্য লক", "WARNING")
        _statusBanner.value = "ফোন $minutes মিনিটের জন্য লক করা হয়েছে"
    }

    fun cancelImmediateLock() {
        curfewManager.cancelImmediateLock()
        _curfewConfig.value = curfewManager.getCurfewConfig()
        _isCurfewLockActive.value = curfewManager.isCurfewCurrentlyActive()
        _statusBanner.value = "তাৎক্ষণিক লক প্রত্যাহার করা হয়েছে"
    }

    fun toggleAllowedApp(packageName: String) {
        curfewManager.toggleAllowedApp(packageName)
        _curfewConfig.value = curfewManager.getCurfewConfig()
    }

    fun addAllowedApp(packageName: String) {
        if (packageName.isBlank()) return
        val config = curfewManager.getCurfewConfig()
        val updated = config.allowedAppPackages + packageName.trim()
        val newConfig = config.copy(allowedAppPackages = updated)
        curfewManager.saveCurfewConfig(newConfig)
        _curfewConfig.value = newConfig
    }

    fun removeAllowedApp(packageName: String) {
        val config = curfewManager.getCurfewConfig()
        val updated = config.allowedAppPackages - packageName.trim()
        val newConfig = config.copy(allowedAppPackages = updated)
        curfewManager.saveCurfewConfig(newConfig)
        _curfewConfig.value = newConfig
    }

    fun setAudioMode(mode: AudioMode) {
        _selectedAudioMode.value = mode
    }

    // Islamic Quran Audio Player Methods
    fun onPlaySurah(surah: QuranSurah, mode: AudioMode = _selectedAudioMode.value) {
        quranAudioManager.playOrPauseSurah(surah, mode)
    }

    fun onDownloadSurah(surah: QuranSurah, mode: AudioMode = _selectedAudioMode.value) {
        quranAudioManager.downloadSurahAudio(surah, mode) { success ->
            _statusBanner.value = if (success) "${surah.nameBangla} (${mode.titleBangla}) ডাউনলোড সফল হয়েছে" else "ডাউনলোড ব্যর্থ হয়েছে"
        }
    }

    fun onDeleteDownloadedSurah(surahNumber: Int, mode: AudioMode = _selectedAudioMode.value) {
        quranAudioManager.deleteDownloadedSurah(surahNumber, mode)
        _statusBanner.value = "ডাউনলোড ফাইল মুছে ফেলা হয়েছে"
    }

    // 1-minute quick strict test lock helper
    fun testOneMinuteLock() {
        val cal = java.util.Calendar.getInstance()
        val curH = cal.get(java.util.Calendar.HOUR_OF_DAY)
        val curM = cal.get(java.util.Calendar.MINUTE)
        val endM = (curM + 1) % 60
        val endH = if (curM + 1 >= 60) (curH + 1) % 24 else curH
        val newConfig = _curfewConfig.value.copy(
            isEnabled = true,
            startHour = curH,
            startMinute = curM,
            endHour = endH,
            endMinute = endM,
            isStrictUnbreakableLock = true
        )
        updateCurfewConfig(newConfig)
        // Immediately launch full-screen unbreakable lock and hardware device lock
        CurfewLockActivity.startLock(context)
        DeviceAdminHelper.lockDeviceNow(context)
        _statusBanner.value = "১ মিনিটের কড়া টেস্ট লক সক্রিয় করা হয়েছে ($curH:${String.format("%02d", curM)} থেকে $endH:${String.format("%02d", endM)})"
        logSecurityEvent("CURFEW_LOCK", "Test Lock Triggered", "1-minute strict unbreakable test phone lock", "ALERT")
    }

    // Language switch
    fun setLanguage(language: AppLanguage) {
        languageManager.setLanguage(language)
        _appLanguage.value = language
        _statusBanner.value = if (language == AppLanguage.BANGLA) "ভাষা বাংলায় পরিবর্তন করা হয়েছে" else "Language changed to English"
    }

    private fun logSecurityEvent(type: String, title: String, details: String, severity: String) {
        viewModelScope.launch(Dispatchers.IO) {
            database.securityLogDao().insertLog(
                SecurityLogEntity(
                    eventType = type,
                    title = title,
                    details = details,
                    severity = severity
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        quranAudioManager.stopPlayback()
    }
}
