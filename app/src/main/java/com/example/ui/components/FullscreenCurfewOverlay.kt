package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curfew.CurfewConfig
import com.example.ui.theme.CyberDanger
import com.example.ui.theme.CyberPrimary
import com.example.ui.theme.CyberSecondary
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextPrimary
import com.example.ui.theme.CyberTextSecondary
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun FullscreenCurfewOverlay(
    config: CurfewConfig,
    isBangla: Boolean = true,
    onRequestEmergencyUnlock: () -> Unit
) {
    // Live Countdown Timer
    var remainingSeconds by remember { mutableStateOf(calculateRemainingSeconds(config)) }

    LaunchedEffect(config) {
        while (true) {
            remainingSeconds = calculateRemainingSeconds(config)
            delay(1000)
        }
    }

    val hours = remainingSeconds / 3600
    val minutes = (remainingSeconds % 3600) / 60
    val seconds = remainingSeconds % 60

    val countdownText = String.format("%02d : %02d : %02d", hours, minutes, seconds)

    val startTimeFormatted = formatTimeDisplay(config.startHour, config.startMinute)
    val endTimeFormatted = formatTimeDisplay(config.endHour, config.endMinute)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xF5040812))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Icon Badge
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(CyberDanger.copy(alpha = 0.15f))
                    .border(2.dp, CyberDanger.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (config.isStrictUnbreakableLock) Icons.Default.LockClock else Icons.Default.Bedtime,
                    contentDescription = "Lock Active",
                    tint = CyberDanger,
                    modifier = Modifier.size(52.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (config.isStrictUnbreakableLock) "কড়া লক মোড সক্রিয়" else "কার্ফিউ লক সক্রিয়",
                color = CyberDanger,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = config.reasonMessage,
                color = CyberTextPrimary,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Time Range Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(CyberSurface)
                    .border(1.dp, CyberSecondary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "নির্ধারিত লক সময়",
                        color = CyberTextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$startTimeFormatted  থেকে  $endTimeFormatted পর্যন্ত",
                        color = CyberSecondary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Live Countdown Timer Display
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F172A))
                    .border(1.dp, CyberDanger.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.HourglassBottom,
                        contentDescription = null,
                        tint = CyberDanger,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "স্বয়ংক্রিয় আনলক হতে বাকি সময়",
                        color = CyberTextMuted,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = countdownText,
                    color = CyberDanger,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "ঘণ্টা : মিনিট : সেকেন্ড",
                    color = CyberTextMuted,
                    fontSize = 10.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Strict vs Standard Unlocking Behavior
            if (config.isStrictUnbreakableLock) {
                // Strict unbreakable mode: NO bypass allowed!
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(CyberDanger.copy(alpha = 0.12f))
                        .border(1.dp, CyberDanger.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "⛔ আনলক বন্ধ (Strict Lock)",
                            color = CyberDanger,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "আপনি কড়া লক সক্রিয় করেছেন। নির্ধারিত সময় ($endTimeFormatted) পার না হওয়া পর্যন্ত পিন দিয়েও খোলা সম্ভব নয়। সময় শেষ হলে ফোন নিজে থেকেই খুলে যাবে।",
                            color = CyberTextSecondary,
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp
                        )
                    }
                }
            } else {
                // Standard mode allows emergency PIN unlock
                OutlinedButton(
                    onClick = onRequestEmergencyUnlock,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberPrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(0.85f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "জরুরি প্রয়োজনে আনলক (পিন কোড)", fontSize = 12.sp)
                }
            }
        }
    }
}

private fun calculateRemainingSeconds(config: CurfewConfig): Long {
    val cal = Calendar.getInstance()
    val currentHour = cal.get(Calendar.HOUR_OF_DAY)
    val currentMinute = cal.get(Calendar.MINUTE)
    val currentSecond = cal.get(Calendar.SECOND)

    val currentTotalSeconds = (currentHour * 3600) + (currentMinute * 60) + currentSecond
    val endTotalSeconds = (config.endHour * 3600) + (config.endMinute * 60)

    val diffSeconds = if (endTotalSeconds > currentTotalSeconds) {
        endTotalSeconds - currentTotalSeconds
    } else {
        (24 * 3600) - currentTotalSeconds + endTotalSeconds
    }

    return diffSeconds.toLong().coerceAtLeast(0L)
}

private fun formatTimeDisplay(hour: Int, minute: Int): String {
    val isPm = hour >= 12
    val displayHour = when {
        hour == 0 -> 12
        hour > 12 -> hour - 12
        else -> hour
    }
    val period = if (isPm) "PM" else "AM"
    val minStr = String.format("%02d", minute)
    return "$displayHour:$minStr $period"
}
