package com.example.curfew

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R
import com.example.admin.DeviceAdminHelper

class CurfewReceiver : BroadcastReceiver() {

    companion object {
        private const val CHANNEL_ID = "safeguard_curfew_channel"
        private const val NOTIFICATION_ID = 8899
    }

    override fun onReceive(context: Context, intent: Intent?) {
        val curfewManager = CurfewManager(context)
        val config = curfewManager.getCurfewConfig()
        if (!config.isEnabled) return

        when (intent?.action) {
            CurfewAlarmScheduler.ACTION_CURFEW_START -> {
                if (curfewManager.isCurfewCurrentlyActive()) {
                    // 1. Launch system-wide curfew lock activity
                    CurfewLockActivity.startLock(context)

                    // 2. Hardware device screen lock if admin enabled
                    DeviceAdminHelper.lockDeviceNow(context)

                    // 3. High priority notification
                    showNotification(
                        context,
                        title = "পুরো ফোন কার্ফিউ লক সক্রিয়",
                        message = "নির্ধারিত সময় শেষ না হওয়া পর্যন্ত ফোন সুরক্ষিত ও লক থাকবে।"
                    )
                }
            }

            CurfewAlarmScheduler.ACTION_CURFEW_END -> {
                // Curfew period ended!
                showNotification(
                    context,
                    title = "কার্ফিউ সময় সমাপ্ত",
                    message = "নির্ধারিত সময় শেষ হয়েছে। পুরো ফোন আনলক করা হয়েছে।"
                )

                // Reschedule for next occurrence
                CurfewAlarmScheduler.scheduleCurfewAlarms(context, config)
            }
        }
    }

    private fun showNotification(context: Context, title: String, message: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager ?: return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "SafeGuard Curfew Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifies when phone bedtime curfew starts and ends"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val openIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
