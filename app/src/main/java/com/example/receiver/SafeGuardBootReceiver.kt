package com.example.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.admin.DeviceAdminHelper
import com.example.curfew.CurfewAlarmScheduler
import com.example.curfew.CurfewLockActivity
import com.example.curfew.CurfewManager

class SafeGuardBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Restore Curfew Lock schedule
            val curfewManager = CurfewManager(context)
            val curfewConfig = curfewManager.getCurfewConfig()
            if (curfewConfig.isEnabled) {
                CurfewAlarmScheduler.scheduleCurfewAlarms(context, curfewConfig)
                if (curfewManager.isCurfewCurrentlyActive()) {
                    CurfewLockActivity.startLock(context)
                    DeviceAdminHelper.lockDeviceNow(context)
                }
            }
        }
    }
}
