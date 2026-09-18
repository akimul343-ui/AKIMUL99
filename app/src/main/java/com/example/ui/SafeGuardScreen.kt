package com.example.ui

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.SecurityLogEntity
import com.example.islamic.IslamicContentRepository
import com.example.ui.components.BedtimeLockTab
import com.example.ui.components.FeatureCatalogSection
import com.example.ui.components.FullscreenCurfewOverlay
import com.example.ui.components.IslamicRecoveryTab
import com.example.ui.components.MasterShieldButton
import com.example.ui.components.MetricCard
import com.example.ui.components.PinDialog
import com.example.ui.components.RenderAdminTab
import com.example.ui.components.SetupPinDialog
import com.example.ui.theme.CyberBackground
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberDanger
import com.example.ui.theme.CyberPrimary
import com.example.ui.theme.CyberSecondary
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceHover
import com.example.ui.theme.CyberSurfaceVariant
import com.example.ui.theme.CyberTertiary
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextPrimary
import com.example.ui.theme.CyberTextSecondary
import com.example.ui.theme.CyberWarning
import com.example.util.AppLanguage
import com.example.util.AppStrings
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafeGuardScreen(
    viewModel: SafeGuardViewModel,
    activity: Activity,
    modifier: Modifier = Modifier
) {
    val currentTab by viewModel.currentTab.collectAsState()
    val isShieldActive by viewModel.isShieldActive.collectAsState()
    val isDeviceAdminActive by viewModel.isDeviceAdminActive.collectAsState()
    val isPinConfigured by viewModel.isPinConfigured.collectAsState()
    val pendingPinAction by viewModel.pendingPinAction.collectAsState()
    val showSetupPinDialog by viewModel.showSetupPinDialog.collectAsState()
    val curfewConfig by viewModel.curfewConfig.collectAsState()
    val isCurfewLockActive by viewModel.isCurfewLockActive.collectAsState()
    val stats by viewModel.securityStats.collectAsState()
    val recentLogs by viewModel.recentLogs.collectAsState()
    val selectedAudioMode by viewModel.selectedAudioMode.collectAsState()
    val playerState by viewModel.quranAudioManager.playerState.collectAsState()
    val statusBanner by viewModel.statusBanner.collectAsState()
    val appLanguage by viewModel.appLanguage.collectAsState()
    val isBangla = appLanguage == AppLanguage.BANGLA

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = CyberBackground,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(CyberPrimary.copy(alpha = 0.15f))
                                .border(1.dp, CyberPrimary, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = "Safe Guard Logo",
                                tint = CyberPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "SAFE GUARD",
                                color = CyberTextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = if (isShieldActive) {
                                    if (isBangla) "ফোন ও কার্ফিউ লক সক্রিয়" else "Curfew Guard Active"
                                } else {
                                    if (isBangla) "সুরক্ষা শিল্ড নিষ্ক্রিয়" else "Shield Inactive"
                                },
                                color = if (isShieldActive) CyberSecondary else CyberDanger,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                },
                navigationIcon = {
                    if (currentTab != SafeGuardTab.OVERVIEW) {
                        IconButton(
                            onClick = { viewModel.selectTab(SafeGuardTab.OVERVIEW) },
                            modifier = Modifier.testTag("top_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = CyberTextPrimary
                            )
                        }
                    }
                },
                actions = {
                    // Render Cloud Admin Shortcut Icon
                    IconButton(
                        onClick = { viewModel.selectTab(SafeGuardTab.RENDER_ADMIN) },
                        modifier = Modifier.testTag("render_admin_top_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudSync,
                            contentDescription = "Render Admin",
                            tint = if (currentTab == SafeGuardTab.RENDER_ADMIN) CyberPrimary else CyberTextSecondary
                        )
                    }

                    // Language Switch Icon
                    IconButton(
                        onClick = {
                            viewModel.setLanguage(if (isBangla) AppLanguage.ENGLISH else AppLanguage.BANGLA)
                        },
                        modifier = Modifier.testTag("language_switch_button")
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(CyberSurfaceVariant)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (isBangla) "EN" else "বাং",
                                color = CyberPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CyberSurface
                )
            )
        },
        bottomBar = {
            SafeGuardBottomNav(
                currentTab = currentTab,
                isBangla = isBangla,
                onSelectTab = { viewModel.selectTab(it) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Status Notification Banner
                AnimatedVisibility(
                    visible = statusBanner != null,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    statusBanner?.let { message ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(CyberSurfaceVariant)
                                .border(1.dp, CyberPrimary, RoundedCornerShape(0.dp))
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = CyberSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = message,
                                        color = CyberTextPrimary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                IconButton(
                                    onClick = { viewModel.clearStatusBanner() },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Dismiss",
                                        tint = CyberTextMuted,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Active Tab Content
                when (currentTab) {
                    SafeGuardTab.OVERVIEW -> {
                        OverviewTabContent(
                            isShieldActive = isShieldActive,
                            isDeviceAdminActive = isDeviceAdminActive,
                            isCurfewActive = isCurfewLockActive,
                            curfewConfig = curfewConfig,
                            stats = stats,
                            isBangla = isBangla,
                            onToggleShield = { viewModel.toggleMasterShield() },
                            onTriggerTestLock = { viewModel.testOneMinuteLock() },
                            onNavigateTab = { viewModel.selectTab(it) }
                        )
                    }

                    SafeGuardTab.BEDTIME_LOCK -> {
                        BedtimeLockTab(
                            curfewConfig = curfewConfig,
                            isLockCurrentlyActive = isCurfewLockActive,
                            onUpdateConfig = { viewModel.updateCurfewConfig(it) },
                            onRequestEmergencyOverride = { viewModel.requestEmergencyCurfewOverride() },
                            onLockNowUntil = { h, m, label -> viewModel.lockNowUntil(h, m, label) },
                            onLockNowForMinutes = { mins, label -> viewModel.lockNowForMinutes(mins, label) },
                            onCancelImmediateLock = { viewModel.cancelImmediateLock() },
                            onToggleAllowedApp = { viewModel.toggleAllowedApp(it) },
                            onAddAllowedApp = { viewModel.addAllowedApp(it) },
                            onRemoveAllowedApp = { viewModel.removeAllowedApp(it) }
                        )
                    }

                    SafeGuardTab.ISLAMIC_RECOVERY -> {
                        IslamicRecoveryTab(
                            surahs = viewModel.surahList,
                            advices = IslamicContentRepository.antiAddictionAdvices,
                            hadithBooks = IslamicContentRepository.hadithBooks,
                            islamicPoems = IslamicContentRepository.islamicPoems,
                            playerState = playerState,
                            selectedAudioMode = selectedAudioMode,
                            onSetAudioMode = { viewModel.setAudioMode(it) },
                            onPlaySurah = { surah, mode -> viewModel.onPlaySurah(surah, mode) },
                            onDownloadSurah = { surah, mode -> viewModel.onDownloadSurah(surah, mode) },
                            onDeleteSurah = { surahNumber, mode -> viewModel.onDeleteDownloadedSurah(surahNumber, mode) }
                        )
                    }

                    SafeGuardTab.ANTI_UNINSTALL -> {
                        AntiUninstallTabContent(
                            isDeviceAdminActive = isDeviceAdminActive,
                            isPinConfigured = isPinConfigured,
                            isBangla = isBangla,
                            onEnableDeviceAdmin = { viewModel.requestEnableDeviceAdmin(activity) },
                            onDisableDeviceAdmin = { viewModel.requestDeactivateAdmin() },
                            onChangePin = { viewModel.requestChangePin() }
                        )
                    }

                    SafeGuardTab.RENDER_ADMIN -> {
                        RenderAdminTab(
                            renderManager = viewModel.renderAdminManager,
                            isLockCurrentlyActive = isCurfewLockActive,
                            allowedAppsCount = curfewConfig.allowedAppPackages.size,
                            onLockNowForMinutes = { mins, label -> viewModel.lockNowForMinutes(mins, label) },
                            onCancelImmediateLock = { viewModel.cancelImmediateLock() }
                        )
                    }

                    SafeGuardTab.ACTIVITY_LOGS -> {
                        ActivityLogsTabContent(
                            logs = recentLogs,
                            isBangla = isBangla,
                            onClearLogs = { viewModel.requestClearLogs() }
                        )
                    }

                    SafeGuardTab.SETTINGS -> {
                        SettingsTabContent(
                            isBangla = isBangla,
                            isPinConfigured = isPinConfigured,
                            onToggleLanguage = {
                                viewModel.setLanguage(if (isBangla) AppLanguage.ENGLISH else AppLanguage.BANGLA)
                            },
                            onChangePin = { viewModel.requestChangePin() }
                        )
                    }
                }
            }

            // PIN Input Dialog for Protected Actions
            if (pendingPinAction != null) {
                PinDialog(
                    actionTitle = when (pendingPinAction) {
                        is PinAction.DeactivateShield -> if (isBangla) "শিল্ড নিষ্ক্রিয়করণ অনুমোদন" else "Confirm Shield Deactivation"
                        is PinAction.DeactivateAdmin -> if (isBangla) "ডিভাইস অ্যাডমিন নিষ্ক্রিয়করণ" else "Confirm Admin Removal"
                        is PinAction.ChangePin -> if (isBangla) "মাস্টার পিন পরিবর্তন" else "Verify Current Master PIN"
                        is PinAction.EmergencyCurfewOverride -> if (isBangla) "জরুরি আনলক অনুমোদন" else "Emergency Override Authentication"
                        is PinAction.ClearActivityLogs -> if (isBangla) "লগ মুছে ফেলার অনুমোদন" else "Confirm Clear Logs"
                        null -> ""
                    },
                    onVerifyPin = { pin -> viewModel.verifyPin(pin) },
                    onSuccess = { viewModel.onPinSuccess() },
                    onDismiss = { viewModel.dismissPinDialog() },
                    securityQuestion = viewModel.getSecurityQuestion(),
                    onVerifySecurityAnswer = { ans -> viewModel.verifySecurityAnswer(ans) },
                    isLockedOut = viewModel.isLockedOut(),
                    lockoutSecondsRemaining = viewModel.getRemainingLockoutSeconds()
                )
            }

            // Setup or Reset Master PIN Dialog
            if (showSetupPinDialog) {
                SetupPinDialog(
                    isInitialSetup = !isPinConfigured,
                    onSavePin = { pin, q, a ->
                        viewModel.setupMasterPin(pin, q, a)
                    },
                    onDismiss = { viewModel.dismissSetupPinDialog() }
                )
            }

            // Fullscreen Curfew Active Overlay
            if (isCurfewLockActive) {
                FullscreenCurfewOverlay(
                    config = curfewConfig,
                    isBangla = isBangla,
                    onRequestEmergencyUnlock = { viewModel.requestEmergencyCurfewOverride() }
                )
            }
        }
    }
}

@Composable
fun SafeGuardBottomNav(
    currentTab: SafeGuardTab,
    isBangla: Boolean,
    onSelectTab: (SafeGuardTab) -> Unit
) {
    NavigationBar(
        containerColor = CyberSurface,
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        NavigationBarItem(
            selected = currentTab == SafeGuardTab.OVERVIEW,
            onClick = { onSelectTab(SafeGuardTab.OVERVIEW) },
            icon = { Icon(Icons.Default.Shield, contentDescription = "Overview") },
            label = { Text(if (isBangla) "হোম" else "Home", fontSize = 10.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CyberPrimary,
                selectedTextColor = CyberPrimary,
                indicatorColor = CyberSurfaceVariant,
                unselectedIconColor = CyberTextMuted,
                unselectedTextColor = CyberTextMuted
            ),
            modifier = Modifier.testTag("tab_overview")
        )

        NavigationBarItem(
            selected = currentTab == SafeGuardTab.BEDTIME_LOCK,
            onClick = { onSelectTab(SafeGuardTab.BEDTIME_LOCK) },
            icon = { Icon(Icons.Default.Bedtime, contentDescription = "Curfew") },
            label = { Text(if (isBangla) "ফোন লক" else "Curfew", fontSize = 10.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CyberWarning,
                selectedTextColor = CyberWarning,
                indicatorColor = CyberSurfaceVariant,
                unselectedIconColor = CyberTextMuted,
                unselectedTextColor = CyberTextMuted
            ),
            modifier = Modifier.testTag("tab_bedtime")
        )

        NavigationBarItem(
            selected = currentTab == SafeGuardTab.ISLAMIC_RECOVERY,
            onClick = { onSelectTab(SafeGuardTab.ISLAMIC_RECOVERY) },
            icon = { Icon(Icons.Default.MenuBook, contentDescription = "Quran") },
            label = { Text(if (isBangla) "কুরআন" else "Quran", fontSize = 10.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CyberSecondary,
                selectedTextColor = CyberSecondary,
                indicatorColor = CyberSurfaceVariant,
                unselectedIconColor = CyberTextMuted,
                unselectedTextColor = CyberTextMuted
            ),
            modifier = Modifier.testTag("tab_islamic")
        )

        NavigationBarItem(
            selected = currentTab == SafeGuardTab.ANTI_UNINSTALL,
            onClick = { onSelectTab(SafeGuardTab.ANTI_UNINSTALL) },
            icon = { Icon(Icons.Default.Lock, contentDescription = "Tamper") },
            label = { Text(if (isBangla) "অ্যাডমিন" else "Admin", fontSize = 10.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CyberDanger,
                selectedTextColor = CyberDanger,
                indicatorColor = CyberSurfaceVariant,
                unselectedIconColor = CyberTextMuted,
                unselectedTextColor = CyberTextMuted
            ),
            modifier = Modifier.testTag("tab_admin")
        )

        NavigationBarItem(
            selected = currentTab == SafeGuardTab.ACTIVITY_LOGS,
            onClick = { onSelectTab(SafeGuardTab.ACTIVITY_LOGS) },
            icon = { Icon(Icons.Default.History, contentDescription = "Logs") },
            label = { Text(if (isBangla) "লগ" else "Logs", fontSize = 10.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CyberPrimary,
                selectedTextColor = CyberPrimary,
                indicatorColor = CyberSurfaceVariant,
                unselectedIconColor = CyberTextMuted,
                unselectedTextColor = CyberTextMuted
            ),
            modifier = Modifier.testTag("tab_logs")
        )

        NavigationBarItem(
            selected = currentTab == SafeGuardTab.SETTINGS,
            onClick = { onSelectTab(SafeGuardTab.SETTINGS) },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text(if (isBangla) "সেটিংস" else "Settings", fontSize = 10.sp, fontWeight = FontWeight.SemiBold) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = CyberTertiary,
                selectedTextColor = CyberTertiary,
                indicatorColor = CyberSurfaceVariant,
                unselectedIconColor = CyberTextMuted,
                unselectedTextColor = CyberTextMuted
            ),
            modifier = Modifier.testTag("tab_settings")
        )
    }
}

@Composable
fun OverviewTabContent(
    isShieldActive: Boolean,
    isDeviceAdminActive: Boolean,
    isCurfewActive: Boolean,
    curfewConfig: com.example.curfew.CurfewConfig,
    stats: com.example.data.model.PhoneSecurityStats,
    isBangla: Boolean,
    onToggleShield: () -> Unit,
    onTriggerTestLock: () -> Unit,
    onNavigateTab: (SafeGuardTab) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Master Shield Core Button
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(CyberSurface)
                    .border(1.dp, CyberBorder, RoundedCornerShape(20.dp))
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    MasterShieldButton(
                        isActive = isShieldActive,
                        onToggle = onToggleShield,
                        modifier = Modifier.testTag("master_shield_toggle")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = if (isShieldActive) {
                            if (isBangla) "সেফ গার্ড শিল্ড সক্রিয়" else "Safe Guard Shield Active"
                        } else {
                            if (isBangla) "সুরক্ষা বন্ধ রয়েছে" else "Protection Shield Inactive"
                        },
                        color = if (isShieldActive) CyberSecondary else CyberDanger,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = if (isShieldActive) {
                            if (isBangla) "ফোন কার্ফিউ লক ও অ্যান্টি-আনইন্সটল প্রতিরক্ষা চালু আছে" else "Phone lock curfew and anti-uninstall security active"
                        } else {
                            if (isBangla) "শিল্ড চালু করতে উপরের বাটনে চাপ দিন" else "Tap button above to activate protection"
                        },
                        color = CyberTextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        // Quick 1-Minute Test Phone Lock Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CyberSurfaceVariant)
                    .border(1.5.dp, CyberWarning.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(CyberWarning.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = CyberWarning,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Column {
                            Text(
                                text = if (isBangla) "১ মিনিট টেস্ট ফোন লক" else "1-Minute Test Phone Lock",
                                color = CyberTextPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isBangla) "এখনই ফুলস্ক্রিন ফোন লক পরীক্ষা করুন" else "Test full-screen phone lock screen instantly",
                                color = CyberTextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Button(
                        onClick = onTriggerTestLock,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CyberWarning,
                            contentColor = CyberBackground
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("one_minute_test_lock_btn")
                    ) {
                        Text(
                            text = if (isBangla) "লক করুন" else "Lock Now",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Security Status Metric Cards Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MetricCard(
                        title = if (isBangla) "কার্ফিউ লক" else "Curfew Lock",
                        count = stats.curfewLocksEnforced,
                        subtitle = if (curfewConfig.isEnabled) "${curfewConfig.startHour}:00 - ${curfewConfig.endHour}:00" else "নিষ্ক্রিয়",
                        icon = Icons.Default.Bedtime,
                        accentColor = CyberWarning,
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        title = if (isBangla) "অ্যান্টি-আনইন্সটল" else "Anti-Uninstall",
                        count = if (isDeviceAdminActive) 1 else 0,
                        subtitle = if (isDeviceAdminActive) "সক্রিয় ও সুরক্ষিত" else "নিষ্ক্রিয়",
                        icon = Icons.Default.Lock,
                        accentColor = if (isDeviceAdminActive) CyberSecondary else CyberDanger,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MetricCard(
                        title = if (isBangla) "রিকভারি স্ট্রিক" else "Clean Streak",
                        count = stats.recoveryDaysStreak,
                        subtitle = if (isBangla) "দিন আসক্তিমুক্ত" else "Days sober",
                        icon = Icons.Default.CheckCircle,
                        accentColor = CyberSecondary,
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        title = if (isBangla) "পিন নিরাপত্তা" else "PIN Protection",
                        count = stats.pinVerifications,
                        subtitle = if (isBangla) "মাস্টার কোড সেট" else "Protected",
                        icon = Icons.Default.AdminPanelSettings,
                        accentColor = CyberPrimary,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Modular Features Hub / Shortcuts
        item {
            FeatureCatalogSection(
                isBangla = isBangla,
                onNavigateTab = onNavigateTab
            )
        }
    }
}

@Composable
fun AntiUninstallTabContent(
    isDeviceAdminActive: Boolean,
    isPinConfigured: Boolean,
    isBangla: Boolean,
    onEnableDeviceAdmin: () -> Unit,
    onDisableDeviceAdmin: () -> Unit,
    onChangePin: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CyberSurface)
                    .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
                    .padding(18.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(CyberDanger.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = CyberDanger,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Column {
                            Text(
                                text = if (isBangla) "অ্যান্টি-আনইন্সটল ও ট্যাম্পার গার্ড" else "Anti-Uninstall & Tamper Guard",
                                color = CyberTextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isDeviceAdminActive) {
                                    if (isBangla) "ডিভাইস অ্যাডমিন সক্রিয় রয়েছে" else "Device Admin is currently Active"
                                } else {
                                    if (isBangla) "ডিভাইস অ্যাডমিন সক্রিয় করা হয়নি" else "Device Admin is Inactive"
                                },
                                color = if (isDeviceAdminActive) CyberSecondary else CyberDanger,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Text(
                        text = if (isBangla) {
                            "ডিভাইস অ্যাডমিন সক্রিয় থাকলে কেউ ইচ্ছা করলেই অ্যাপটি সরাসরি আনইন্সটল বা জোরপূর্বক বন্ধ করতে পারবে না। ফোন কার্ফিউ লক নিশ্চিত করতে এটি অপরিহার্য।"
                        } else {
                            "Device Administrator permission prevents accidental uninstallation or unauthorized app bypassing during curfew lockdowns."
                        },
                        color = CyberTextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )

                    if (!isDeviceAdminActive) {
                        Button(
                            onClick = onEnableDeviceAdmin,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CyberDanger,
                                contentColor = CyberTextPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().testTag("enable_device_admin_btn")
                        ) {
                            Text(
                                text = if (isBangla) "ডিভাইস অ্যাডমিন চালু করুন" else "Activate Device Admin",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        OutlinedButton(
                            onClick = onDisableDeviceAdmin,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = CyberTextSecondary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().testTag("disable_device_admin_btn")
                        ) {
                            Text(
                                text = if (isBangla) "অ্যাডমিন বন্ধ করুন (পিন প্রয়োজন)" else "Deactivate Admin (PIN Required)",
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // Master PIN Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CyberSurface)
                    .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
                    .padding(18.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isBangla) "মাস্টার অ্যাডমিন পিন" else "Master Administrator PIN",
                            color = CyberTextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (isPinConfigured) {
                                if (isBangla) "গোপন পিন ও নিরাপত্তা প্রশ্ন সেট আছে" else "PIN code configured & encrypted"
                            } else {
                                if (isBangla) "এখনও পিন সেট করা হয়নি" else "PIN not configured"
                            },
                            color = CyberTextSecondary,
                            fontSize = 11.sp
                        )
                    }

                    Button(
                        onClick = onChangePin,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CyberSecondary,
                            contentColor = CyberBackground
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("change_pin_btn")
                    ) {
                        Text(
                            text = if (isPinConfigured) {
                                if (isBangla) "পিন পরিবর্তন" else "Change PIN"
                            } else {
                                if (isBangla) "পিন সেট করুন" else "Setup PIN"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityLogsTabContent(
    logs: List<SecurityLogEntity>,
    isBangla: Boolean,
    onClearLogs: () -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = if (isBangla) "নিরাপত্তা ও কার্ফিউ অডিট লগ" else "Security & Curfew Audit Logs",
                        color = CyberTextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isBangla) "মোট ${logs.size}টি ইভেন্ট সংরক্ষিত" else "${logs.size} recorded events",
                        color = CyberTextSecondary,
                        fontSize = 12.sp
                    )
                }

                if (logs.isNotEmpty()) {
                    IconButton(
                        onClick = onClearLogs,
                        modifier = Modifier.testTag("clear_logs_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Clear Logs",
                            tint = CyberDanger
                        )
                    }
                }
            }
        }

        if (logs.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = CyberSecondary,
                            modifier = Modifier.size(42.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = if (isBangla) "কোনো সন্দেহজনক ঘটনা বা লঙ্ঘন নেই" else "No security incidents recorded",
                            color = CyberTextSecondary,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        } else {
            items(logs) { log ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(CyberSurface)
                        .border(1.dp, CyberBorder, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(
                                    when (log.severity) {
                                        "ALERT" -> CyberDanger.copy(alpha = 0.15f)
                                        "WARNING" -> CyberWarning.copy(alpha = 0.15f)
                                        "SUCCESS" -> CyberSecondary.copy(alpha = 0.15f)
                                        else -> CyberPrimary.copy(alpha = 0.15f)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = when (log.eventType) {
                                    "CURFEW_LOCK" -> Icons.Default.LockClock
                                    "DEVICE_ADMIN" -> Icons.Default.AdminPanelSettings
                                    "PIN_SECURITY" -> Icons.Default.Lock
                                    else -> Icons.Default.Shield
                                },
                                contentDescription = null,
                                tint = when (log.severity) {
                                    "ALERT" -> CyberDanger
                                    "WARNING" -> CyberWarning
                                    "SUCCESS" -> CyberSecondary
                                    else -> CyberPrimary
                                },
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = log.title,
                                    color = CyberTextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = dateFormat.format(Date(log.timestamp)),
                                    color = CyberTextMuted,
                                    fontSize = 10.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = log.details,
                                color = CyberTextSecondary,
                                fontSize = 11.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsTabContent(
    isBangla: Boolean,
    isPinConfigured: Boolean,
    onToggleLanguage: () -> Unit,
    onChangePin: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = if (isBangla) "অ্যাপ সেটিংস" else "Application Settings",
                color = CyberTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Language Switch
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CyberSurface)
                    .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isBangla) "ভাষা পরিবর্তন (Language)" else "App Language",
                            color = CyberTextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isBangla) "বর্তমান ভাষা: বাংলা" else "Current Language: English",
                            color = CyberTextSecondary,
                            fontSize = 11.sp
                        )
                    }

                    Button(
                        onClick = onToggleLanguage,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CyberPrimary,
                            contentColor = CyberBackground
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("settings_lang_btn")
                    ) {
                        Text(
                            text = if (isBangla) "Switch to English" else "বাংলায় পরিবর্তন",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        // Master PIN Config
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CyberSurface)
                    .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isBangla) "অ্যাডমিন পিন কোড" else "Master Admin PIN",
                            color = CyberTextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isPinConfigured) {
                                if (isBangla) "পিন সক্রিয় রয়েছে" else "PIN Protection is active"
                            } else {
                                if (isBangla) "এখনই মাস্টার পিন সেট করুন" else "Setup Master PIN"
                            },
                            color = CyberTextSecondary,
                            fontSize = 11.sp
                        )
                    }

                    Button(
                        onClick = onChangePin,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CyberSecondary,
                            contentColor = CyberBackground
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = if (isBangla) "পরিবর্তন" else "Change",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        // About Safe Guard
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CyberSurface)
                    .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Safe Guard Pro",
                        color = CyberTextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "ভার্সন: 1.0.0",
                        color = CyberSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "• নাইট কার্ফিউ ও শিডিউল ফোন লক\n• ১ মিনিট তাৎক্ষণিক কড়া টেস্ট লক\n• অডিও কুরআন ও আত্মশুদ্ধি রিকভারি\n• ডিভাইস অ্যাডমিন অ্যান্টি-আনইন্সটল ও মাস্টার পিন",
                        color = CyberTextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}
