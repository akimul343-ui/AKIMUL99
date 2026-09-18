package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.admin.RenderAdminConfig
import com.example.admin.RenderAdminManager
import com.example.ui.theme.CyberBackground
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberDanger
import com.example.ui.theme.CyberPrimary
import com.example.ui.theme.CyberSecondary
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceVariant
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextPrimary
import com.example.ui.theme.CyberTextSecondary
import com.example.ui.theme.CyberWarning
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RenderAdminTab(
    renderManager: RenderAdminManager,
    isLockCurrentlyActive: Boolean,
    allowedAppsCount: Int,
    onLockNowForMinutes: (Int, String) -> Unit,
    onCancelImmediateLock: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var config by remember { mutableStateOf(renderManager.getConfig()) }
    var serverUrlInput by remember(config.serverUrl) { mutableStateOf(config.serverUrl) }
    var secretKeyInput by remember(config.secretKey) { mutableStateOf(config.secretKey) }
    var deviceNameInput by remember(config.deviceName) { mutableStateOf(config.deviceName) }

    var isPinging by remember { mutableStateOf(false) }
    var pingResultMsg by remember { mutableStateOf("") }
    var isPushingTelemetry by remember { mutableStateOf(false) }
    var telemetryResultMsg by remember { mutableStateOf("") }
    var showCodeSnippet by remember { mutableStateOf(false) }

    val formattedLastSync = remember(config.lastSyncTimestamp) {
        if (config.lastSyncTimestamp > 0) {
            SimpleDateFormat("hh:mm:ss a, dd MMM", Locale.getDefault()).format(Date(config.lastSyncTimestamp))
        } else {
            "কখনো সিঙ্ক হয়নি"
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF0F172A), Color(0xFF1E1B4B))
                        )
                    )
                    .border(1.dp, CyberPrimary.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                    .padding(18.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(CyberPrimary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudSync,
                            contentDescription = "Render Admin",
                            tint = CyberPrimary,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "রেন্ডার ক্লাউড অ্যাডমিন ড্যাশবোর্ড (Render.com)",
                        color = CyberTextPrimary,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "রেন্ডার ক্লাউড সার্ভারের সাথে মোবাইল সংযুক্ত করে যে কোনো ব্রাউজার বা দূরবর্তী স্থান থেকে ফোন লক ও কনফিগার পরিচালনা করুন।",
                        color = CyberTextSecondary,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (config.lastSyncTimestamp > 0) Color(0xFF10B981).copy(alpha = 0.2f) else CyberWarning.copy(alpha = 0.2f))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "স্ট্যাটাস: ${config.lastSyncStatus}",
                                color = if (config.lastSyncTimestamp > 0) Color(0xFF10B981) else CyberWarning,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // Card 1: Render Server Connection Configuration
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CyberSurface)
                    .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cloud,
                            contentDescription = null,
                            tint = CyberPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "রেন্ডার সার্ভার কনফিগারেশন",
                            color = CyberTextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedTextField(
                        value = serverUrlInput,
                        onValueChange = { serverUrlInput = it },
                        label = { Text("রেন্ডার সার্ভার URL (Render App URL)", fontSize = 11.sp) },
                        placeholder = { Text("https://my-app.onrender.com") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyberPrimary,
                            unfocusedBorderColor = CyberBorder
                        )
                    )

                    OutlinedTextField(
                        value = secretKeyInput,
                        onValueChange = { secretKeyInput = it },
                        label = { Text("অ্যাডমিন সিক্রেট টোকেন (Secret Token)", fontSize = 11.sp) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyberPrimary,
                            unfocusedBorderColor = CyberBorder
                        )
                    )

                    OutlinedTextField(
                        value = deviceNameInput,
                        onValueChange = { deviceNameInput = it },
                        label = { Text("ডিভাইসের নাম (Device Identifier)", fontSize = 11.sp) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyberPrimary,
                            unfocusedBorderColor = CyberBorder
                        )
                    )

                    // Auto-sync switch
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text("অটো রিমোট ব্যাকগ্রাউন্ড সিঙ্ক", color = CyberTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text("সার্ভারের সাথে নিয়মিত স্ট্যাটাস সিঙ্ক রাখবে", color = CyberTextMuted, fontSize = 10.sp)
                        }

                        Switch(
                            checked = config.isRemoteSyncEnabled,
                            onCheckedChange = { enabled ->
                                val updated = config.copy(
                                    serverUrl = serverUrlInput,
                                    secretKey = secretKeyInput,
                                    deviceName = deviceNameInput,
                                    isRemoteSyncEnabled = enabled
                                )
                                config = updated
                                renderManager.saveConfig(updated)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = CyberBackground,
                                checkedTrackColor = CyberPrimary
                            )
                        )
                    }

                    // Action Buttons: Ping & Save
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                isPinging = true
                                pingResultMsg = ""
                                scope.launch {
                                    val result = renderManager.pingRenderServer(serverUrlInput, secretKeyInput)
                                    isPinging = false
                                    pingResultMsg = result.second
                                    config = renderManager.getConfig()
                                }
                            },
                            enabled = !isPinging,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, CyberPrimary),
                            modifier = Modifier.weight(1f).height(42.dp)
                        ) {
                            if (isPinging) {
                                CircularProgressIndicator(modifier = Modifier.size(16.dp), color = CyberPrimary, strokeWidth = 2.dp)
                            } else {
                                Icon(Icons.Default.Sensors, contentDescription = null, modifier = Modifier.size(16.dp), tint = CyberPrimary)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("পিং টেস্ট", fontSize = 11.sp, color = CyberPrimary)
                            }
                        }

                        Button(
                            onClick = {
                                val updated = config.copy(
                                    serverUrl = serverUrlInput,
                                    secretKey = secretKeyInput,
                                    deviceName = deviceNameInput
                                )
                                config = updated
                                renderManager.saveConfig(updated)
                                Toast.makeText(context, "রেন্ডার সেটিংস সংরক্ষিত হয়েছে!", Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CyberPrimary, contentColor = CyberBackground),
                            modifier = Modifier.weight(1f).height(42.dp)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("সংরক্ষণ", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    if (pingResultMsg.isNotBlank()) {
                        Text(
                            text = pingResultMsg,
                            color = if (pingResultMsg.contains("সফল")) Color(0xFF10B981) else CyberDanger,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Card 2: Remote Telemetry & Quick Cloud Action
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CyberSurface)
                    .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhoneAndroid,
                            contentDescription = null,
                            tint = CyberSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "লাইভ ডিভাইস টেলিমেট্রি ও রিমোট টেস্ট",
                            color = CyberSecondary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "ডিভাইসের বর্তমান অবস্থা: ${if (isLockCurrentlyActive) "🔒 পুরো ফোন লক সক্রিয়" else "🟢 স্বাভাবিক মোড"}\nঅনুমোদিত অ্যাপস: $allowedAppsCount টি\nসর্বশেষ সিঙ্ক: $formattedLastSync",
                        color = CyberTextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )

                    Button(
                        onClick = {
                            isPushingTelemetry = true
                            telemetryResultMsg = ""
                            scope.launch {
                                val res = renderManager.pushDeviceTelemetry(
                                    isLocked = isLockCurrentlyActive,
                                    remainingSeconds = 3600L,
                                    allowedAppsCount = allowedAppsCount
                                )
                                isPushingTelemetry = false
                                telemetryResultMsg = res.second
                                config = renderManager.getConfig()
                            }
                        },
                        enabled = !isPushingTelemetry,
                        colors = ButtonDefaults.buttonColors(containerColor = CyberSecondary, contentColor = CyberBackground),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().height(42.dp)
                    ) {
                        if (isPushingTelemetry) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), color = CyberBackground, strokeWidth = 2.dp)
                        } else {
                            Icon(Icons.Default.Sync, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("রেন্ডারে ডিভাইস ডেটা পুশ করুন", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    if (telemetryResultMsg.isNotBlank()) {
                        Text(
                            text = telemetryResultMsg,
                            color = if (telemetryResultMsg.contains("সফল")) Color(0xFF10B981) else CyberDanger,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        // Card 3: Free 1-Click Render.com Deployment Guide & Code
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF0F172A))
                    .border(1.dp, CyberWarning.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudDone,
                            contentDescription = null,
                            tint = CyberWarning,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Render.com ফ্রি ডিপ্লয় গাইড ও ব্যাকএন্ড কোড",
                            color = CyberWarning,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "১. Render.com এ একটি ফ্রি অ্যাকাউন্ট খুলে 'New Web Service' নির্বাচন করুন।\n২. নিচে দেওয়া Node.js Express কোডটি ব্যবহার করে বিনামূল্যে সার্ভার রান করুন।\n৩. আপনার নিজস্ব Render URL পেয়ে গেলে উপরে বসিয়ে পুরো ফোন পরিচালনা করুন।",
                        color = CyberTextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Render Backend Code", renderManager.getRenderNodeJsBackendCode())
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "রেন্ডার সার্ভার কোড ক্লিপবোর্ডে কপি হয়েছে!", Toast.LENGTH_LONG).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CyberWarning, contentColor = CyberBackground),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).height(38.dp)
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("কোড কপি করুন", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = { showCodeSnippet = !showCodeSnippet },
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, CyberWarning),
                            modifier = Modifier.weight(1f).height(38.dp)
                        ) {
                            Text(if (showCodeSnippet) "কোড লুকান" else "কোড দেখুন", fontSize = 11.sp, color = CyberWarning)
                        }
                    }

                    if (showCodeSnippet) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(CyberBackground)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = renderManager.getRenderNodeJsBackendCode(),
                                color = CyberTextSecondary,
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace,
                                lineHeight = 12.sp
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
