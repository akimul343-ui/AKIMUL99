package com.example.curfew

import android.content.Context
import android.content.SharedPreferences
import java.util.Calendar

data class CurfewConfig(
    val isEnabled: Boolean = false,
    val startHour: Int = 23,     // 11 PM
    val startMinute: Int = 0,
    val endHour: Int = 4,        // 4 AM
    val endMinute: Int = 0,
    val isStrictUnbreakableLock: Boolean = false, // "খুলবে না কিন্তু চাইলেও যেন খুলতে পারে না ঠিক ওই টাইম শেষ হওয়ার পর যেন ফোন খুলে"
    val emergencyPinRequired: Boolean = true,
    val reasonMessage: String = "নির্ধারিত সময় লক রয়েছে। আত্মনিয়ন্ত্রণ ও ইবাদতের জন্য ফোন ব্যবহার বন্ধ রাখা হয়েছে।",
    val allowedAppPackages: Set<String> = setOf("com.intercloud.brilliant", "com.google.android.dialer", "com.android.dialer", "com.samsung.android.dialer"),
    val allowEmergencyCalls: Boolean = true,
    val immediateLockUntilTimestamp: Long = 0L,
    val immediateLockTargetLabel: String = ""
)

class CurfewManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("safeguard_curfew_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_CURFEW_ENABLED = "curfew_enabled"
        private const val KEY_START_HOUR = "curfew_start_hour"
        private const val KEY_START_MINUTE = "curfew_start_minute"
        private const val KEY_END_HOUR = "curfew_end_hour"
        private const val KEY_END_MINUTE = "curfew_end_minute"
        private const val KEY_STRICT_LOCK = "curfew_strict_lock"
        private const val KEY_MESSAGE = "curfew_message"
        private const val KEY_TEMP_EMERGENCY_UNLOCK_UNTIL = "curfew_temp_unlock_until"
        private const val KEY_ALLOWED_APPS = "curfew_allowed_apps"
        private const val KEY_ALLOW_EMERGENCY = "curfew_allow_emergency"
        private const val KEY_IMMEDIATE_LOCK_UNTIL = "curfew_immediate_lock_until"
        private const val KEY_IMMEDIATE_LOCK_LABEL = "curfew_immediate_lock_label"
    }

    fun getCurfewConfig(): CurfewConfig {
        val defaultAllowed = setOf("com.intercloud.brilliant", "com.google.android.dialer", "com.android.dialer", "com.samsung.android.dialer")
        val savedAllowed = prefs.getStringSet(KEY_ALLOWED_APPS, defaultAllowed) ?: defaultAllowed

        return CurfewConfig(
            isEnabled = prefs.getBoolean(KEY_CURFEW_ENABLED, false),
            startHour = prefs.getInt(KEY_START_HOUR, 23),
            startMinute = prefs.getInt(KEY_START_MINUTE, 0),
            endHour = prefs.getInt(KEY_END_HOUR, 4),
            endMinute = prefs.getInt(KEY_END_MINUTE, 0),
            isStrictUnbreakableLock = prefs.getBoolean(KEY_STRICT_LOCK, false),
            emergencyPinRequired = true,
            reasonMessage = prefs.getString(
                KEY_MESSAGE,
                "নির্ধারিত সময় লক রয়েছে। আত্মনিয়ন্ত্রণ ও ইবাদতের জন্য ফোন ব্যবহার বন্ধ রাখা হয়েছে।"
            ) ?: "",
            allowedAppPackages = savedAllowed,
            allowEmergencyCalls = prefs.getBoolean(KEY_ALLOW_EMERGENCY, true),
            immediateLockUntilTimestamp = prefs.getLong(KEY_IMMEDIATE_LOCK_UNTIL, 0L),
            immediateLockTargetLabel = prefs.getString(KEY_IMMEDIATE_LOCK_LABEL, "") ?: ""
        )
    }

    fun saveCurfewConfig(config: CurfewConfig) {
        prefs.edit()
            .putBoolean(KEY_CURFEW_ENABLED, config.isEnabled)
            .putInt(KEY_START_HOUR, config.startHour)
            .putInt(KEY_START_MINUTE, config.startMinute)
            .putInt(KEY_END_HOUR, config.endHour)
            .putInt(KEY_END_MINUTE, config.endMinute)
            .putBoolean(KEY_STRICT_LOCK, config.isStrictUnbreakableLock)
            .putString(KEY_MESSAGE, config.reasonMessage)
            .putStringSet(KEY_ALLOWED_APPS, config.allowedAppPackages)
            .putBoolean(KEY_ALLOW_EMERGENCY, config.allowEmergencyCalls)
            .putLong(KEY_IMMEDIATE_LOCK_UNTIL, config.immediateLockUntilTimestamp)
            .putString(KEY_IMMEDIATE_LOCK_LABEL, config.immediateLockTargetLabel)
            .apply()
    }

    /**
     * Checks if current local time falls into active curfew lockdown period
     * OR if an immediate lock countdown is active.
     */
    fun isCurfewCurrentlyActive(): Boolean {
        val config = getCurfewConfig()
        val now = System.currentTimeMillis()

        // 1. Check Immediate Lock (e.g. "এখন থেকে রাত ১২:০০ টা পর্যন্ত বা ভোর ৪:০০ টা পর্যন্ত লক")
        if (config.immediateLockUntilTimestamp > now) {
            return true
        }

        // 2. Check Scheduled Curfew
        if (!config.isEnabled) return false

        // If strict unbreakable lock is ON, emergency unlock is completely disallowed!
        if (!config.isStrictUnbreakableLock) {
            val tempUnlockUntil = prefs.getLong(KEY_TEMP_EMERGENCY_UNLOCK_UNTIL, 0L)
            if (now < tempUnlockUntil) {
                return false
            }
        }

        val cal = Calendar.getInstance()
        val currentHour = cal.get(Calendar.HOUR_OF_DAY)
        val currentMinute = cal.get(Calendar.MINUTE)
        val currentTotalMinutes = currentHour * 60 + currentMinute

        val startTotalMinutes = config.startHour * 60 + config.startMinute
        val endTotalMinutes = config.endHour * 60 + config.endMinute

        return if (startTotalMinutes == endTotalMinutes) {
            false
        } else if (startTotalMinutes < endTotalMinutes) {
            // Same-day curfew (e.g. 6:40 to 6:41, or 14:00 to 18:00)
            currentTotalMinutes in startTotalMinutes until endTotalMinutes
        } else {
            // Overnight curfew spanning midnight (e.g. 23:00 to 04:00)
            currentTotalMinutes >= startTotalMinutes || currentTotalMinutes < endTotalMinutes
        }
    }

    /**
     * Returns remaining seconds until curfew unlock time.
     */
    fun getRemainingSeconds(): Long {
        val config = getCurfewConfig()
        val now = System.currentTimeMillis()

        // Check immediate lock remaining seconds
        if (config.immediateLockUntilTimestamp > now) {
            return ((config.immediateLockUntilTimestamp - now) / 1000L).coerceAtLeast(0L)
        }

        val cal = Calendar.getInstance()
        val currentHour = cal.get(Calendar.HOUR_OF_DAY)
        val currentMinute = cal.get(Calendar.MINUTE)
        val currentSecond = cal.get(Calendar.SECOND)

        val currentTotalSeconds = (currentHour * 3600) + (currentMinute * 60) + currentSecond
        val endTotalSeconds = (config.endHour * 3600) + (config.endMinute * 60)

        val diffSeconds = if (endTotalSeconds > currentTotalSeconds) {
            endTotalSeconds - currentTotalSeconds
        } else {
            (24 * 3600) - currentTotalSeconds + endTotalSeconds
        }

        return diffSeconds.toLong().coerceAtLeast(0L)
    }

    /**
     * Starts an immediate lock from right now until a specific hour and minute (e.g. 12:00 AM / 00:00 or 04:00 AM).
     */
    fun startImmediateLockUntil(targetHour: Int, targetMinute: Int, label: String) {
        val cal = Calendar.getInstance()
        val currentHour = cal.get(Calendar.HOUR_OF_DAY)
        val currentMinute = cal.get(Calendar.MINUTE)

        val targetCal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, targetHour)
            set(Calendar.MINUTE, targetMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // If target is in the past for today, target tomorrow (e.g. current 23:00, target 00:00 midnight)
        if (targetCal.timeInMillis <= cal.timeInMillis) {
            targetCal.add(Calendar.DAY_OF_YEAR, 1)
        }

        val config = getCurfewConfig().copy(
            isEnabled = true,
            isStrictUnbreakableLock = true,
            immediateLockUntilTimestamp = targetCal.timeInMillis,
            immediateLockTargetLabel = label
        )
        saveCurfewConfig(config)
    }

    /**
     * Starts an immediate lock for a specified number of minutes (e.g. 1 minute test lock or 60 minutes).
     */
    fun startImmediateLockForMinutes(minutes: Int, label: String) {
        val untilTimestamp = System.currentTimeMillis() + (minutes * 60 * 1000L)
        val config = getCurfewConfig().copy(
            isEnabled = true,
            isStrictUnbreakableLock = true,
            immediateLockUntilTimestamp = untilTimestamp,
            immediateLockTargetLabel = label
        )
        saveCurfewConfig(config)
    }

    fun cancelImmediateLock() {
        val config = getCurfewConfig().copy(
            immediateLockUntilTimestamp = 0L,
            immediateLockTargetLabel = ""
        )
        saveCurfewConfig(config)
    }

    fun isAppAllowed(packageName: String): Boolean {
        val config = getCurfewConfig()
        if (config.allowEmergencyCalls && (packageName.contains("dialer", ignoreCase = true) || packageName.contains("telecom", ignoreCase = true))) {
            return true
        }
        return config.allowedAppPackages.contains(packageName)
    }

    fun toggleAllowedApp(packageName: String) {
        val config = getCurfewConfig()
        val newSet = config.allowedAppPackages.toMutableSet()
        if (newSet.contains(packageName)) {
            newSet.remove(packageName)
        } else {
            newSet.add(packageName)
        }
        saveCurfewConfig(config.copy(allowedAppPackages = newSet))
    }

    fun grantEmergencyUnlock(durationMinutes: Int = 10) {
        val config = getCurfewConfig()
        if (config.isStrictUnbreakableLock) {
            // Unbreakable lock cannot be bypassed!
            return
        }
        val unlockUntil = System.currentTimeMillis() + (durationMinutes * 60 * 1000L)
        prefs.edit().putLong(KEY_TEMP_EMERGENCY_UNLOCK_UNTIL, unlockUntil).apply()
    }

    fun formatTimeString(hour: Int, minute: Int): String {
        val isPm = hour >= 12
        val displayHour = when {
            hour == 0 -> 12
            hour > 12 -> hour - 12
            else -> hour
        }
        val period = if (isPm) "PM" else "AM"
        val minStr = String.format("%02d", minute)
        return "$displayHour:$minStr $period"
    }

    fun formatBanglaTimeString(hour: Int, minute: Int): String {
        val period = when {
            hour in 4..11 -> "সকাল"
            hour in 12..15 -> "দুপুর"
            hour in 16..17 -> "বিকাল"
            hour in 18..19 -> "সন্ধ্যা"
            else -> "রাত"
        }
        val displayHour = when {
            hour == 0 -> 12
            hour > 12 -> hour - 12
            else -> hour
        }
        val minStr = String.format("%02d", minute)
        return "$period $displayHour:$minStr"
    }
}
