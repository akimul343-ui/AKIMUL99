package com.example.security

import android.content.Context
import android.content.SharedPreferences
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Locale

class SecurityManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("safeguard_admin_security", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_PIN_HASH = "key_pin_hash"
        private const val KEY_PIN_SALT = "key_pin_salt"
        private const val KEY_SECURITY_QUESTION = "key_sec_question"
        private const val KEY_SECURITY_ANSWER_HASH = "key_sec_ans_hash"
        private const val KEY_FAILED_ATTEMPTS = "key_failed_attempts"
        private const val KEY_LOCKOUT_UNTIL = "key_lockout_until"
        private const val KEY_SHIELD_ENABLED = "key_shield_enabled"
        private const val KEY_RECOVERY_STREAK = "key_recovery_streak"
        private const val KEY_LAST_STREAK_DATE = "key_last_streak_date"
        private const val MAX_FAILED_ATTEMPTS = 5
        private const val LOCKOUT_DURATION_MS = 30_000L // 30 seconds lockout
    }

    /**
     * Checks if administrator PIN has been created.
     */
    fun isPinConfigured(): Boolean {
        return prefs.getString(KEY_PIN_HASH, null) != null
    }

    /**
     * Sets or updates the Master PIN with random salt and SHA-256 hashing.
     */
    fun setMasterPin(pin: String, question: String, answer: String): Boolean {
        if (pin.length < 4) return false
        val salt = generateSalt()
        val hash = hashWithSalt(pin, salt)
        val answerClean = answer.trim().lowercase(Locale.ROOT)
        val answerHash = hashWithSalt(answerClean, salt)

        prefs.edit()
            .putString(KEY_PIN_HASH, hash)
            .putString(KEY_PIN_SALT, salt)
            .putString(KEY_SECURITY_QUESTION, question.trim())
            .putString(KEY_SECURITY_ANSWER_HASH, answerHash)
            .putInt(KEY_FAILED_ATTEMPTS, 0)
            .putLong(KEY_LOCKOUT_UNTIL, 0L)
            .apply()
        return true
    }

    /**
     * Verifies the provided PIN against the stored hash.
     */
    fun verifyPin(pin: String): Boolean {
        if (isLockedOut()) return false
        val storedHash = prefs.getString(KEY_PIN_HASH, null) ?: return false
        val storedSalt = prefs.getString(KEY_PIN_SALT, null) ?: return false
        val inputHash = hashWithSalt(pin, storedSalt)
        val isValid = (inputHash == storedHash)

        if (isValid) {
            resetFailedAttempts()
        } else {
            recordFailedAttempt()
        }
        return isValid
    }

    /**
     * Verifies the security recovery answer to reset PIN.
     */
    fun verifySecurityAnswer(answer: String): Boolean {
        val storedAnswerHash = prefs.getString(KEY_SECURITY_ANSWER_HASH, null) ?: return false
        val storedSalt = prefs.getString(KEY_PIN_SALT, null) ?: return false
        val inputHash = hashWithSalt(answer.trim().lowercase(Locale.ROOT), storedSalt)
        return inputHash == storedAnswerHash
    }

    fun getSecurityQuestion(): String {
        return prefs.getString(KEY_SECURITY_QUESTION, "Security Recovery Question")
            ?: "Security Recovery Question"
    }

    fun isLockedOut(): Boolean {
        val lockoutUntil = prefs.getLong(KEY_LOCKOUT_UNTIL, 0L)
        return System.currentTimeMillis() < lockoutUntil
    }

    fun getRemainingLockoutSeconds(): Long {
        val lockoutUntil = prefs.getLong(KEY_LOCKOUT_UNTIL, 0L)
        val diff = lockoutUntil - System.currentTimeMillis()
        return if (diff > 0) diff / 1000 else 0
    }

    private fun recordFailedAttempt() {
        val current = prefs.getInt(KEY_FAILED_ATTEMPTS, 0) + 1
        val editor = prefs.edit().putInt(KEY_FAILED_ATTEMPTS, current)
        if (current >= MAX_FAILED_ATTEMPTS) {
            editor.putLong(KEY_LOCKOUT_UNTIL, System.currentTimeMillis() + LOCKOUT_DURATION_MS)
        }
        editor.apply()
    }

    private fun resetFailedAttempts() {
        prefs.edit()
            .putInt(KEY_FAILED_ATTEMPTS, 0)
            .putLong(KEY_LOCKOUT_UNTIL, 0L)
            .apply()
    }

    // Master Shield State
    fun isShieldEnabled(): Boolean {
        return prefs.getBoolean(KEY_SHIELD_ENABLED, true)
    }

    fun setShieldEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_SHIELD_ENABLED, enabled).apply()
    }

    // Islamic Recovery Streak
    fun getRecoveryStreak(): Int {
        return prefs.getInt(KEY_RECOVERY_STREAK, 7)
    }

    fun incrementStreak() {
        val current = getRecoveryStreak()
        prefs.edit().putInt(KEY_RECOVERY_STREAK, current + 1).apply()
    }

    fun resetStreak() {
        prefs.edit().putInt(KEY_RECOVERY_STREAK, 0).apply()
    }

    private fun generateSalt(): String {
        val random = SecureRandom()
        val saltBytes = ByteArray(16)
        random.nextBytes(saltBytes)
        return saltBytes.joinToString("") { "%02x".format(it) }
    }

    private fun hashWithSalt(input: String, salt: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val combined = "$salt:$input"
        val bytes = md.digest(combined.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
