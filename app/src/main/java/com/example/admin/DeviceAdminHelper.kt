package com.example.admin

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.example.receiver.SafeGuardDeviceAdminReceiver

object DeviceAdminHelper {

    fun getAdminComponent(context: Context): ComponentName {
        return ComponentName(context, SafeGuardDeviceAdminReceiver::class.java)
    }

    fun isDeviceAdminActive(context: Context): Boolean {
        val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as? DevicePolicyManager
        return dpm?.isAdminActive(getAdminComponent(context)) ?: false
    }

    fun isAdminActive(context: Context): Boolean = isDeviceAdminActive(context)

    fun requestDeviceAdmin(activity: android.app.Activity) {
        val intent = createEnableAdminIntent(activity)
        activity.startActivity(intent)
    }

    fun removeDeviceAdmin(context: Context) {
        deactivateAdmin(context)
    }

    /**
     * Immediately locks the physical phone screen using Android Device Administration.
     */
    fun lockDeviceNow(context: Context): Boolean {
        val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as? DevicePolicyManager
        if (dpm != null && isDeviceAdminActive(context)) {
            try {
                dpm.lockNow()
                return true
            } catch (e: Exception) {
                return false
            }
        }
        return false
    }

    fun createEnableAdminIntent(context: Context): Intent {
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, getAdminComponent(context))
        intent.putExtra(
            DevicePolicyManager.EXTRA_ADD_EXPLANATION,
            "Activating Safe Guard Device Admin locks the application to prevent unauthorized uninstallation or force-stop by unauthorized users or children without the administrator PIN."
        )
        return intent
    }

    fun deactivateAdmin(context: Context) {
        val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as? DevicePolicyManager
        dpm?.removeActiveAdmin(getAdminComponent(context))
    }
}
