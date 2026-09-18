package com.example.admin

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

data class RenderAdminConfig(
    val serverUrl: String = "https://safe-guard-admin.onrender.com",
    val secretKey: String = "safeguard_secret_admin_token",
    val isRemoteSyncEnabled: Boolean = false,
    val lastSyncTimestamp: Long = 0L,
    val lastSyncStatus: String = "অপেক্ষমাণ",
    val deviceName: String = "${Build.MANUFACTURER} ${Build.MODEL}"
)

class RenderAdminManager(private val context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("safeguard_render_admin_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_SERVER_URL = "render_server_url"
        private const val KEY_SECRET_KEY = "render_secret_key"
        private const val KEY_REMOTE_SYNC = "render_remote_sync"
        private const val KEY_LAST_SYNC_TIME = "render_last_sync_time"
        private const val KEY_LAST_SYNC_STATUS = "render_last_sync_status"
        private const val KEY_DEVICE_NAME = "render_device_name"
    }

    fun getConfig(): RenderAdminConfig {
        return RenderAdminConfig(
            serverUrl = prefs.getString(KEY_SERVER_URL, "https://safe-guard-admin.onrender.com") ?: "https://safe-guard-admin.onrender.com",
            secretKey = prefs.getString(KEY_SECRET_KEY, "safeguard_secret_admin_token") ?: "safeguard_secret_admin_token",
            isRemoteSyncEnabled = prefs.getBoolean(KEY_REMOTE_SYNC, false),
            lastSyncTimestamp = prefs.getLong(KEY_LAST_SYNC_TIME, 0L),
            lastSyncStatus = prefs.getString(KEY_LAST_SYNC_STATUS, "সংযুক্ত নয়") ?: "সংযুক্ত নয়",
            deviceName = prefs.getString(KEY_DEVICE_NAME, "${Build.MANUFACTURER} ${Build.MODEL}") ?: "${Build.MANUFACTURER} ${Build.MODEL}"
        )
    }

    fun saveConfig(config: RenderAdminConfig) {
        prefs.edit()
            .putString(KEY_SERVER_URL, config.serverUrl.trim())
            .putString(KEY_SECRET_KEY, config.secretKey.trim())
            .putBoolean(KEY_REMOTE_SYNC, config.isRemoteSyncEnabled)
            .putLong(KEY_LAST_SYNC_TIME, config.lastSyncTimestamp)
            .putString(KEY_LAST_SYNC_STATUS, config.lastSyncStatus)
            .putString(KEY_DEVICE_NAME, config.deviceName)
            .apply()
    }

    /**
     * Pings the user's Render web service endpoint (e.g. GET /api/health or /ping)
     */
    suspend fun pingRenderServer(targetUrl: String, secretKey: String): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        try {
            val cleanUrl = if (targetUrl.endsWith("/")) targetUrl.dropLast(1) else targetUrl
            val pingEndpoint = if (cleanUrl.contains("/api")) "$cleanUrl/ping" else "$cleanUrl/api/ping"
            val url = URL(pingEndpoint)
            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 8000
                readTimeout = 8000
                setRequestProperty("Authorization", "Bearer $secretKey")
                setRequestProperty("User-Agent", "SafeGuard-Android/2.0")
            }

            val responseCode = connection.responseCode
            if (responseCode in 200..299) {
                saveConfig(getConfig().copy(lastSyncTimestamp = System.currentTimeMillis(), lastSyncStatus = "সফলভাবে সংযুক্ত (HTTP $responseCode)"))
                Pair(true, "রেন্ডার সার্ভারের সাথে সফলভাবে সংযোগ স্থাপিত হয়েছে! (HTTP $responseCode)")
            } else {
                Pair(false, "সার্ভার রেসপন্স কোড: HTTP $responseCode")
            }
        } catch (e: Exception) {
            Pair(false, "রেন্ডার সংযোগ ব্যর্থ: ${e.localizedMessage ?: "নেটওয়ার্ক কানেকশন চেক করুন"}")
        }
    }

    /**
     * Sends telemetry heartbeat to Render with lock state & remaining time
     */
    suspend fun pushDeviceTelemetry(
        isLocked: Boolean,
        remainingSeconds: Long,
        allowedAppsCount: Int
    ): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        val config = getConfig()
        try {
            val cleanUrl = if (config.serverUrl.endsWith("/")) config.serverUrl.dropLast(1) else config.serverUrl
            val telemetryEndpoint = if (cleanUrl.contains("/api")) "$cleanUrl/telemetry" else "$cleanUrl/api/telemetry"
            val url = URL(telemetryEndpoint)
            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                doOutput = true
                connectTimeout = 10000
                readTimeout = 10000
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Authorization", "Bearer ${config.secretKey}")
                setRequestProperty("User-Agent", "SafeGuard-Android/2.0")
            }

            val payload = JSONObject().apply {
                put("deviceName", config.deviceName)
                put("isLocked", isLocked)
                put("remainingSeconds", remainingSeconds)
                put("allowedAppsCount", allowedAppsCount)
                put("batteryLevel", 100)
                put("timestamp", System.currentTimeMillis())
            }

            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(payload.toString())
                writer.flush()
            }

            val responseCode = connection.responseCode
            if (responseCode in 200..299) {
                saveConfig(config.copy(lastSyncTimestamp = System.currentTimeMillis(), lastSyncStatus = "টেলিমেট্রি সিঙ্ক সম্পন্ন"))
                Pair(true, "রেন্ডার সার্ভারে ডিভাইস স্ট্যাটাস আপডেট সফল!")
            } else {
                Pair(false, "সার্ভার ত্রুটি: HTTP $responseCode")
            }
        } catch (e: Exception) {
            Pair(false, "টেলিমেট্রি পুশ ব্যর্থ: ${e.localizedMessage ?: "নেটওয়ার্ক সমস্যা"}")
        }
    }

    /**
     * Generates modern, ready-to-deploy Node.js Express server code for Render.com.
     * The user can copy this with 1-click and deploy on render.com for free!
     */
    fun getRenderNodeJsBackendCode(): String {
        return """
// ==========================================
// SafeGuard Cloud Admin Server for Render.com
// Deploy on Render: Web Service (Node.js)
// ==========================================
const express = require('express');
const app = express();
app.use(express.json());

const ADMIN_SECRET = process.env.ADMIN_SECRET || 'safeguard_secret_admin_token';
let deviceState = {
  deviceName: 'SafeGuard Device',
  isLocked: false,
  remainingSeconds: 0,
  lastSeen: new Date().toISOString(),
  commandPending: null
};

// Authentication Middleware
const authMiddleware = (req, res, next) => {
  const authHeader = req.headers['authorization'];
  if (!authHeader || authHeader !== `Bearer ${'$'}{ADMIN_SECRET}`) {
    return res.status(401).json({ error: 'Unauthorized: Invalid Secret Token' });
  }
  next();
};

// Ping Endpoint
app.get('/api/ping', authMiddleware, (req, res) => {
  res.json({ status: 'ok', server: 'SafeGuard Render Admin', time: new Date() });
});

// Telemetry Endpoint from Android App
app.post('/api/telemetry', authMiddleware, (req, res) => {
  deviceState = { ...deviceState, ...req.body, lastSeen: new Date().toISOString() };
  const command = deviceState.commandPending;
  deviceState.commandPending = null; // Clear command after delivering
  res.json({ success: true, command: command });
});

// Remote Lock Trigger (from Web Dashboard)
app.post('/api/command/lock', (req, res) => {
  deviceState.commandPending = { action: 'LOCK_NOW', durationMinutes: req.body.duration || 60 };
  res.json({ success: true, message: 'Lock command queued for device' });
});

// Remote Unlock Trigger
app.post('/api/command/unlock', (req, res) => {
  deviceState.commandPending = { action: 'UNLOCK_NOW' };
  res.json({ success: true, message: 'Unlock command queued for device' });
});

// Web Admin Dashboard
app.get('/', (req, res) => {
  res.send(`
    <!DOCTYPE html>
    <html lang="bn">
    <head>
      <meta charset="UTF-8">
      <title>Safe Guard Render Admin Portal</title>
      <style>
        body { background: #0A0F1D; color: #E2E8F0; font-family: sans-serif; padding: 30px; text-align: center; }
        .card { background: #131D33; border: 1px solid #1E293B; border-radius: 16px; padding: 25px; max-width: 500px; margin: 0 auto; box-shadow: 0 10px 30px rgba(0,0,0,0.5); }
        h1 { color: #38BDF8; margin-bottom: 5px; }
        .badge { display: inline-block; padding: 6px 14px; border-radius: 20px; font-weight: bold; margin: 15px 0; background: ${'$'}{deviceState.isLocked ? '#DC2626' : '#10B981'}; }
        button { background: #38BDF8; color: #0A0F1D; border: none; padding: 12px 24px; font-size: 15px; font-weight: bold; border-radius: 10px; cursor: pointer; margin: 8px; }
        .btn-danger { background: #EF4444; color: white; }
      </style>
    </head>
    <body>
      <div class="card">
        <h1>Safe Guard Cloud Admin</h1>
        <p>রেন্ডার ক্লাউড রিমোট কন্ট্রোলার</p>
        <div class="badge">${'$'}{deviceState.isLocked ? '🔒 ডিভাইস লক অবস্থায় রয়েছে' : '🟢 ডিভাইস স্বাভাবিক/আনলক'}</div>
        <p>ডিভাইস: <b>${'$'}{deviceState.deviceName}</b></p>
        <p>সর্বশেষ আপডেট: ${'$'}{new Date(deviceState.lastSeen).toLocaleTimeString()}</p>
        <hr style="border-color: #1E293B; margin: 20px 0;">
        <button class="btn-danger" onclick="fetch('/api/command/lock', {method:'POST'}).then(()=>location.reload())">🔒 রিমোট লক করুন</button>
        <button onclick="fetch('/api/command/unlock', {method:'POST'}).then(()=>location.reload())">🔓 রিমোট আনলক</button>
      </div>
    </body>
    </html>
  `);
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`SafeGuard Admin Server running on port ${'$'}{PORT}`));
        """.trimIndent()
    }
}
