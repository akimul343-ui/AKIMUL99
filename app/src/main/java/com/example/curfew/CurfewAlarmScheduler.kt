package com.example.curfew

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.util.Calendar

object CurfewAlarmScheduler {

    const val ACTION_CURFEW_START = "com.example.safeguard.CURFEW_START"
    const val ACTION_CURFEW_END = "com.example.safeguard.CURFEW_END"

    private const val REQUEST_START = 8001
    private const val REQUEST_END = 8002

    fun scheduleCurfewAlarms(context: Context, config: CurfewConfig) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return

        // Cancel previous alarms
        cancelCurfewAlarms(context)

        if (!config.isEnabled) return

        val now = Calendar.getInstance()

        // Calculate next Start Alarm
        val startCal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, config.startHour)
            set(Calendar.MINUTE, config.startMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(now)) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        // Calculate next End Alarm
        val endCal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, config.endHour)
            set(Calendar.MINUTE, config.endMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(now)) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val startIntent = Intent(context, CurfewReceiver::class.java).apply {
            action = ACTION_CURFEW_START
        }
        val startPending = PendingIntent.getBroadcast(
            context,
            REQUEST_START,
            startIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val endIntent = Intent(context, CurfewReceiver::class.java).apply {
            action = ACTION_CURFEW_END
        }
        val endPending = PendingIntent.getBroadcast(
            context,
            REQUEST_END,
            endIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    startCal.timeInMillis,
                    startPending
                )
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    endCal.timeInMillis,
                    endPending
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    startCal.timeInMillis,
                    startPending
                )
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    endCal.timeInMillis,
                    endPending
                )
            }
        } catch (e: SecurityException) {
            Log.w("CurfewAlarmScheduler", "Exact alarm permission not granted, using normal set: ${e.message}")
            alarmManager.set(AlarmManager.RTC_WAKEUP, startCal.timeInMillis, startPending)
            alarmManager.set(AlarmManager.RTC_WAKEUP, endCal.timeInMillis, endPending)
        }
    }

    fun cancelCurfewAlarms(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val startIntent = Intent(context, CurfewReceiver::class.java).apply {
            action = ACTION_CURFEW_START
        }
        val startPending = PendingIntent.getBroadcast(
            context,
            REQUEST_START,
            startIntent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        if (startPending != null) {
            alarmManager.cancel(startPending)
            startPending.cancel()
        }

        val endIntent = Intent(context, CurfewReceiver::class.java).apply {
            action = ACTION_CURFEW_END
        }
        val endPending = PendingIntent.getBroadcast(
            context,
            REQUEST_END,
            endIntent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        if (endPending != null) {
            alarmManager.cancel(endPending)
            endPending.cancel()
        }
    }
}
