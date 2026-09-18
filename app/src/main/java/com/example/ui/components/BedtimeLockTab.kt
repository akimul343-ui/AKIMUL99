package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curfew.CurfewConfig
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
import java.util.Calendar

@Composable
fun BedtimeLockTab(
    curfewConfig: CurfewConfig,
    isLockCurrentlyActive: Boolean,
    onUpdateConfig: (CurfewConfig) -> Unit,
    onRequestEmergencyOverride: () -> Unit,
    onLockNowUntil: (hour: Int, minute: Int, label: String) -> Unit = { _, _, _ -> },
    onLockNowForMinutes: (minutes: Int, label: String) -> Unit = { _, _ -> },
    onCancelImmediateLock: () -> Unit = {},
    onToggleAllowedApp: (packageName: String) -> Unit = {},
    onAddAllowedApp: (packageName: String) -> Unit = {},
    onRemoveAllowedApp: (packageName: String) -> Unit = {}
) {
    var isEditingHours by remember { mutableStateOf(false) }
    var isCustomLockDialogOpen by remember { mutableStateOf(false) }
    var customTargetHourStr by remember { mutableStateOf("0") }
    var customTargetMinStr by remember { mutableStateOf("0") }
    var newAllowedAppPackage by remember { mutableStateOf("") }

    var startHourStr by remember(curfewConfig.startHour) { mutableStateOf(curfewConfig.startHour.toString()) }
    var startMinStr by remember(curfewConfig.startMinute) { mutableStateOf(curfewConfig.startMinute.toString()) }
    var endHourStr by remember(curfewConfig.endHour) { mutableStateOf(curfewConfig.endHour.toString()) }
    var endMinStr by remember(curfewConfig.endMinute) { mutableStateOf(curfewConfig.endMinute.toString()) }

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
                            colors = listOf(
                                if (isLockCurrentlyActive) Color(0xFF3B0B18) else Color(0xFF131D33),
                                CyberSurface
                            )
                        )
                    )
                    .border(
                        1.dp,
                        if (isLockCurrentlyActive) CyberDanger else CyberPrimary.copy(alpha = 0.5f),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(18.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(
                                if (isLockCurrentlyActive) CyberDanger.copy(alpha = 0.2f)
                                else CyberPrimary.copy(alpha = 0.2f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isLockCurrentlyActive) Icons.Default.Lock else Icons.Default.Bedtime,
                            contentDescription = "Bedtime Lock",
                            tint = if (isLockCurrentlyActive) CyberDanger else CyberPrimary,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "পুরো ফোন সিস্টেম লক ও কারফিউ গার্ড",
                        color = CyberTextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (isLockCurrentlyActive)
                            "⚠️ পুরো ফোন লক রয়েছে! শুধুমাত্র আপনার অনুমোদিত অ্যাপ ও জরুরি কল খোলা যাবে। নির্ধারিত সময়ে স্বয়ংক্রিয়ভাবে লক খুলে যাবে।"
                        else
                            "যে কোনো নির্দিষ্ট সময়ে পুরো ফোন সিস্টেম লক হয়ে যাবে। ওই নির্দিষ্ট সময় শেষ হওয়ার পর পুরো ফোন স্বয়ংক্রিয়ভাবে খুলে যাবে।",
                        color = if (isLockCurrentlyActive) CyberDanger else CyberTextSecondary,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp
                    )

                    if (curfewConfig.immediateLockUntilTimestamp > System.currentTimeMillis()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(CyberDanger.copy(alpha = 0.2f))
                                .border(1.dp, CyberDanger, RoundedCornerShape(8.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "সক্রিয় তাৎক্ষণিক লক: ${curfewConfig.immediateLockTargetLabel}",
                                color = CyberDanger,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Section 1: Instant Quick Lock (User Request: "এখন থেকে রাত ১২:০০ টা বা রাত ৪:০০ টা")
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CyberSurface)
                    .border(1.dp, CyberWarning.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = CyberWarning,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "তাৎক্ষণিক ফোন লক (Quick Lock Now)",
                            color = CyberWarning,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "যেকোনো মুহূর্তে এক ক্লিকে পুরো ফোন লক করে ফেলুন। নির্ধারিত সময়ে ফোন নিজে থেকেই খুলে যাবে:",
                        color = CyberTextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )

                    // Quick Action 1: Lock until 12:00 AM Midnight
                    Button(
                        onClick = {
                            onLockNowUntil(0, 0, "রাত ১২:০০ টা পর্যন্ত কড়া লক")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CyberWarning,
                            contentColor = CyberBackground
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().height(42.dp)
                    ) {
                        Icon(imageVector = Icons.Default.LockClock, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "🌙 এখন থেকে রাত ১২:০০ টা পর্যন্ত ফোন লক", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }

                    // Quick Action 2: Lock until 4:00 AM (Fajr / Tahajjud)
                    Button(
                        onClick = {
                            onLockNowUntil(4, 0, "রাত ৪:০০ টা পর্যন্ত কড়া লক")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6366F1),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().height(42.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Bedtime, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "🌌 এখন থেকে রাত ৪:০০ টা পর্যন্ত ফোন লক", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }

                    // Row: Custom Lock & 1-Minute Test
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { isCustomLockDialogOpen = !isCustomLockDialogOpen },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberPrimary),
                            border = BorderStroke(1.dp, CyberPrimary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f).height(40.dp)
                        ) {
                            Icon(imageVector = Icons.Default.AccessTime, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("কাস্টম সময় লক", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }

                        OutlinedButton(
                            onClick = {
                                onLockNowForMinutes(1, "১ মিনিটের টেস্ট লক")
                            },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberWarning),
                            border = BorderStroke(1.dp, CyberWarning),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f).height(40.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("১ মিনিট টেস্ট লক", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    // Custom Target Input form
                    if (isCustomLockDialogOpen) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(CyberSurfaceVariant)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "কখন পর্যন্ত লক রাখতে চান? (২৪ ঘণ্টার সময় দিন):",
                                color = CyberTextPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    value = customTargetHourStr,
                                    onValueChange = { customTargetHourStr = it.take(2) },
                                    label = { Text("ঘণ্টা (0-23)", fontSize = 10.sp) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = CyberPrimary,
                                        unfocusedBorderColor = CyberBorder
                                    )
                                )

                                OutlinedTextField(
                                    value = customTargetMinStr,
                                    onValueChange = { customTargetMinStr = it.take(2) },
                                    label = { Text("মিনিট (0-59)", fontSize = 10.sp) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = CyberPrimary,
                                        unfocusedBorderColor = CyberBorder
                                    )
                                )
                            }

                            Button(
                                onClick = {
                                    val h = (customTargetHourStr.toIntOrNull() ?: 0).coerceIn(0, 23)
                                    val m = (customTargetMinStr.toIntOrNull() ?: 0).coerceIn(0, 59)
                                    val label = String.format("%02d:%02d পর্যন্ত লক", h, m)
                                    onLockNowUntil(h, m, label)
                                    isCustomLockDialogOpen = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = CyberPrimary,
                                    contentColor = CyberBackground
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("এই সময় পর্যন্ত লক করুন", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }

                    // If immediate lock is active, allow cancelling if not in strict mode
                    if (curfewConfig.immediateLockUntilTimestamp > System.currentTimeMillis()) {
                        if (!curfewConfig.isStrictUnbreakableLock) {
                            OutlinedButton(
                                onClick = onCancelImmediateLock,
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberDanger),
                                border = BorderStroke(1.dp, CyberDanger),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("তাৎক্ষণিক লক বাতিল করুন", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }

        // Section 2: Whitelisted Allowed Apps (User Request: "ব্রিলিয়ান্ট অ্যাপস এর মত একটা অ্যাপস ঢোকা যাবে আর কোন কিছু হবে না... আমি যেটা সিলেক্ট করব")
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CyberSurface)
                    .border(1.dp, Color(0xFF10B981).copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Apps,
                            contentDescription = null,
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "লক চলাকালীন অনুমোদিত অ্যাপস (Whitelisted Apps)",
                            color = Color(0xFF10B981),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "আপনি যে অ্যাপ সিলেক্ট করবেন শুধুমাত্র লক স্ক্রিনে সেটি চালানো যাবে। বাকি সব অ্যাপ স্বয়ংক্রিয়ভাবে ব্লক থাকবে:",
                        color = CyberTextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )

                    // 1. Emergency Dialing (Always Allowed)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(CyberSurfaceVariant)
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(20.dp))
                            Column {
                                Text("জরুরি ফোন কল (Emergency Phone Call)", color = CyberTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                Text("লক স্ক্রিন থেকে সরাসরি যেকোনো নাম্বারে কল করা যাবে", color = CyberTextSecondary, fontSize = 10.sp)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF10B981).copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("অনুমোদিত", color = Color(0xFF10B981), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // 2. Brilliant Connect App (User explicit request)
                    val isBrilliantAllowed = curfewConfig.allowedAppPackages.contains("com.intercloud.brilliant")
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isBrilliantAllowed) Color(0xFF0F261D) else CyberSurfaceVariant)
                            .border(1.dp, if (isBrilliantAllowed) Color(0xFF10B981).copy(alpha = 0.5f) else Color.Transparent, RoundedCornerShape(10.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = CyberPrimary, modifier = Modifier.size(20.dp))
                            Column {
                                Text("ব্রিলিয়ান্ট কানেক্ট (Brilliant Connect)", color = CyberTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                Text("প্যাকেজ: com.intercloud.brilliant", color = CyberTextMuted, fontSize = 10.sp)
                            }
                        }

                        Switch(
                            checked = isBrilliantAllowed,
                            onCheckedChange = {
                                onToggleAllowedApp("com.intercloud.brilliant")
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = CyberBackground,
                                checkedTrackColor = Color(0xFF10B981)
                            )
                        )
                    }

                    // 3. System Clock & Alarm
                    val isClockAllowed = curfewConfig.allowedAppPackages.any { it.contains("clock", ignoreCase = true) }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(CyberSurfaceVariant)
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.AccessTime, contentDescription = null, tint = CyberWarning, modifier = Modifier.size(20.dp))
                            Column {
                                Text("ঘড়ি ও এলার্ম (Clock & Alarm)", color = CyberTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                Text("তাহাজ্জুদ বা ফজর নামাজের অ্যালার্ম বাজবে", color = CyberTextSecondary, fontSize = 10.sp)
                            }
                        }

                        Switch(
                            checked = isClockAllowed,
                            onCheckedChange = {
                                onToggleAllowedApp("com.google.android.deskclock")
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = CyberBackground,
                                checkedTrackColor = CyberPrimary
                            )
                        )
                    }

                    // 4. Custom App Adder
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(CyberSurfaceVariant.copy(alpha = 0.6f))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "অন্য কোনো নির্দিষ্ট অ্যাপ অনুমোদন করতে প্যাকেজ নাম লিখুন:",
                            color = CyberTextSecondary,
                            fontSize = 11.sp
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = newAllowedAppPackage,
                                onValueChange = { newAllowedAppPackage = it },
                                placeholder = { Text("উদাহরণ: com.android.calculator2", fontSize = 10.sp) },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = CyberPrimary,
                                    unfocusedBorderColor = CyberBorder
                                )
                            )

                            Button(
                                onClick = {
                                    if (newAllowedAppPackage.isNotBlank()) {
                                        onAddAllowedApp(newAllowedAppPackage.trim())
                                        newAllowedAppPackage = ""
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = CyberPrimary,
                                    contentColor = CyberBackground
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Add")
                            }
                        }
                    }

                    // List currently allowed apps
                    if (curfewConfig.allowedAppPackages.isNotEmpty()) {
                        Text(
                            text = "বর্তমান অনুমোদিত তালিকা (${curfewConfig.allowedAppPackages.size} টি):",
                            color = CyberTextMuted,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )

                        curfewConfig.allowedAppPackages.forEach { pkg ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(CyberBackground)
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = pkg,
                                    color = CyberTextPrimary,
                                    fontSize = 11.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )

                                IconButton(
                                    onClick = { onRemoveAllowedApp(pkg) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = CyberDanger,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Section 3: Master Switch Card for Daily Schedule
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CyberSurface)
                    .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "দৈনিক শিডিউল কার্ফিউ লক",
                            color = CyberTextPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "প্রতিদিন নির্ধারিত সময়ে স্বয়ংক্রিয়ভাবে পুরো ফোন লক হবে",
                            color = CyberTextSecondary,
                            fontSize = 11.sp
                        )
                    }

                    Switch(
                        checked = curfewConfig.isEnabled,
                        onCheckedChange = { isChecked ->
                            onUpdateConfig(curfewConfig.copy(isEnabled = isChecked))
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = CyberBackground,
                            checkedTrackColor = CyberPrimary,
                            uncheckedThumbColor = CyberTextMuted,
                            uncheckedTrackColor = CyberSurfaceVariant
                        )
                    )
                }
            }
        }

        // Section 4: Strict Unbreakable Mode Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (curfewConfig.isStrictUnbreakableLock) Color(0xFF261019) else CyberSurface)
                    .border(
                        1.dp,
                        if (curfewConfig.isStrictUnbreakableLock) CyberDanger.copy(alpha = 0.8f) else CyberBorder,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(CyberDanger.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = CyberDanger,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "কড়া আনব্রেকেবল লক (Strict Mode)",
                                color = if (curfewConfig.isStrictUnbreakableLock) CyberDanger else CyberTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "সক্রিয় থাকলে নির্ধারিত সময়ের আগে পিন দিয়েও খোলা যাবে না। সময় শেষ হলেই কেবল ফোন আনলক হবে।",
                                color = CyberTextSecondary,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }

                    Switch(
                        checked = curfewConfig.isStrictUnbreakableLock,
                        onCheckedChange = { isStrict ->
                            onUpdateConfig(curfewConfig.copy(isStrictUnbreakableLock = isStrict))
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = CyberBackground,
                            checkedTrackColor = CyberDanger,
                            uncheckedThumbColor = CyberTextMuted,
                            uncheckedTrackColor = CyberSurfaceVariant
                        )
                    )
                }
            }
        }

        // Section 5: Daily Schedule Time Range
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CyberSurface)
                    .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                tint = CyberPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "দৈনিক লক ও স্বয়ংক্রিয় আনলক সময়",
                                color = CyberTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = { isEditingHours = !isEditingHours },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CyberSurfaceVariant,
                                contentColor = CyberPrimary
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = if (isEditingHours) "বাতিল" else "পরিবর্তন", fontSize = 11.sp)
                        }
                    }

                    // Display Time Slots
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(CyberSurfaceVariant)
                                .padding(14.dp)
                        ) {
                            Column {
                                Text(
                                    text = "লক শুরুর সময়",
                                    color = CyberTextMuted,
                                    fontSize = 11.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = String.format("%02d:%02d", curfewConfig.startHour, curfewConfig.startMinute),
                                    color = CyberWarning,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (curfewConfig.startHour >= 12)
                                        "${if (curfewConfig.startHour == 12) 12 else curfewConfig.startHour - 12}:${String.format("%02d", curfewConfig.startMinute)} PM"
                                    else
                                        "${if (curfewConfig.startHour == 0) 12 else curfewConfig.startHour}:${String.format("%02d", curfewConfig.startMinute)} AM",
                                    color = CyberTextSecondary,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(CyberSurfaceVariant)
                                .padding(14.dp)
                        ) {
                            Column {
                                Text(
                                    text = "স্বয়ংক্রিয় আনলক সময়",
                                    color = CyberTextMuted,
                                    fontSize = 11.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = String.format("%02d:%02d", curfewConfig.endHour, curfewConfig.endMinute),
                                    color = CyberSecondary,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (curfewConfig.endHour >= 12)
                                        "${if (curfewConfig.endHour == 12) 12 else curfewConfig.endHour - 12}:${String.format("%02d", curfewConfig.endMinute)} PM"
                                    else
                                        "${if (curfewConfig.endHour == 0) 12 else curfewConfig.endHour}:${String.format("%02d", curfewConfig.endMinute)} AM",
                                    color = CyberTextSecondary,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    // Precise Minute-Level Inputs
                    if (isEditingHours) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(CyberSurfaceVariant)
                                .padding(14.dp)
                        ) {
                            Text(
                                text = "যেকোনো ঘণ্টা ও মিনিট দিন (২৪ ঘণ্টার ফরম্যাট):",
                                color = CyberTextPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "লক শুরুর সময়:",
                                color = CyberWarning,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    value = startHourStr,
                                    onValueChange = { startHourStr = it.take(2) },
                                    label = { Text("ঘণ্টা (0-23)", fontSize = 10.sp) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = CyberWarning,
                                        unfocusedBorderColor = CyberBorder
                                    )
                                )

                                OutlinedTextField(
                                    value = startMinStr,
                                    onValueChange = { startMinStr = it.take(2) },
                                    label = { Text("মিনিট (0-59)", fontSize = 10.sp) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = CyberWarning,
                                        unfocusedBorderColor = CyberBorder
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "আনলক শেষ হওয়ার সময়:",
                                color = CyberSecondary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    value = endHourStr,
                                    onValueChange = { endHourStr = it.take(2) },
                                    label = { Text("ঘণ্টা (0-23)", fontSize = 10.sp) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = CyberSecondary,
                                        unfocusedBorderColor = CyberBorder
                                    )
                                )

                                OutlinedTextField(
                                    value = endMinStr,
                                    onValueChange = { endMinStr = it.take(2) },
                                    label = { Text("মিনিট (0-59)", fontSize = 10.sp) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = CyberSecondary,
                                        unfocusedBorderColor = CyberBorder
                                    )
                                )
                            }

                            Button(
                                onClick = {
                                    val sh = (startHourStr.toIntOrNull() ?: 23).coerceIn(0, 23)
                                    val sm = (startMinStr.toIntOrNull() ?: 0).coerceIn(0, 59)
                                    val eh = (endHourStr.toIntOrNull() ?: 4).coerceIn(0, 23)
                                    val em = (endMinStr.toIntOrNull() ?: 0).coerceIn(0, 59)

                                    onUpdateConfig(
                                        curfewConfig.copy(
                                            startHour = sh,
                                            startMinute = sm,
                                            endHour = eh,
                                            endMinute = em
                                        )
                                    )
                                    isEditingHours = false
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = CyberPrimary,
                                    contentColor = CyberBackground
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("সময় সংরক্ষণ করুন", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Section 6: Emergency Override Option
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CyberSurface)
                    .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "জরুরি সাময়িক আনলক (Emergency Override)",
                        color = CyberTextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (curfewConfig.isStrictUnbreakableLock) {
                        Text(
                            text = "⚠️ কড়া মোড সক্রিয় থাকায় জরুরি আনলক সম্পূর্ণ নিষ্ক্রিয় করা হয়েছে। নির্ধারিত সময় শেষ হওয়ার পরই ফোন স্বয়ংক্রিয়ভাবে আনলক হবে।",
                            color = CyberDanger,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    } else {
                        Text(
                            text = "রাতে জরুরি কাজ বা যোগাযোগের প্রয়োজন হলে অ্যাডমিন পিন ভেরিফাই করে ১৫ মিনিটের জন্য সাময়িক অ্যাক্সেস নেওয়া যাবে।",
                            color = CyberTextSecondary,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedButton(
                            onClick = onRequestEmergencyOverride,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberWarning),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "জরুরি ১৫ মিনিট আনলক", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
