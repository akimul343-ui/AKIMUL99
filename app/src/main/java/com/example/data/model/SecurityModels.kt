package com.example.data.model

enum class SecurityCategory(val displayName: String, val description: String) {
    CURFEW_LOCK("Bedtime Phone Lock", "Curfew enforcement and bedtime device lockouts"),
    DEVICE_ADMIN("Device Administration", "Anti-uninstall and tamper resistance"),
    PIN_SECURITY("Master PIN Security", "Authentication, lockout, and password protection"),
    SPIRITUAL_HABIT("Spiritual Growth", "Quran listening and Islamic recovery milestones"),
    SYSTEM("System Guard", "Boot initialization and system guardian events")
}

data class PhoneSecurityStats(
    val curfewLocksEnforced: Int = 0,
    val tamperAttemptsBlocked: Int = 0,
    val pinVerifications: Int = 0,
    val recoveryDaysStreak: Int = 7
)
