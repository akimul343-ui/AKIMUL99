package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "security_logs")
data class SecurityLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val eventType: String, // CURFEW_LOCK, CURFEW_UNLOCK, DEVICE_ADMIN, TAMPER_ATTEMPT, PIN_VERIFY, EMERGENCY_UNLOCK
    val title: String,
    val details: String,
    val severity: String = "INFO" // INFO, WARNING, SUCCESS, ALERT
)
