package com.example.curfew

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Phone
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.admin.DeviceAdminHelper
import com.example.security.SecurityManager
import com.example.ui.components.PinInputDialog
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
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay

/**
 * Fullscreen System-Wide Phone Lockdown Activity.
 * Intercepts navigation and holds phone lock until the designated time expires.
 * Once the time finishes, it automatically unlocks and closes itself.
 */
class CurfewLockActivity : ComponentActivity() {

    private lateinit var curfewManager: CurfewManager
    private lateinit var securityManager: SecurityManager
    private var isAllowedAppInForeground = false

    companion object {
        fun startLock(context: Context) {
            val intent = Intent(context, CurfewLockActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        curfewManager = CurfewManager(this)
        securityManager = SecurityManager(this)

        // Configure system window flags to show over lock screen
        configureLockWindow()

        // Intercept back button - prevent dismissal
        onBackPressedDispatcher.addCallback(this) {
            // Keep phone locked
        }

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CyberBackground
                ) {
                    CurfewLockScreen(
                        curfewManager = curfewManager,
                        securityManager = securityManager,
                        onLockDeviceHardware = {
                            DeviceAdminHelper.lockDeviceNow(this@CurfewLockActivity)
                        },
                        onEmergencyCall = {
                            isAllowedAppInForeground = true
                            val dialIntent = Intent(Intent.ACTION_DIAL)
                            try {
                                startActivity(dialIntent)
                            } catch (_: Exception) {
                                // Fallback dialer
                            }
                        },
                        onLaunchAllowedApp = { pkg ->
                            isAllowedAppInForeground = true
                            val launchIntent = packageManager.getLaunchIntentForPackage(pkg)
                            if (launchIntent != null) {
                                startActivity(launchIntent)
                            }
                        },
                        onCurfewEnded = {
                            finish()
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        isAllowedAppInForeground = false
        // If curfew has ended while paused, finish immediately
        if (!curfewManager.isCurfewCurrentlyActive()) {
            finish()
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (isAllowedAppInForeground) {
            // User went to allowed app / emergency call
            return
        }
        // If user tries to press Home/Recents while curfew is active, keep enforcing lock
        if (curfewManager.isCurfewCurrentlyActive()) {
            val intent = Intent(this, CurfewLockActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            }
            startActivity(intent)
            DeviceAdminHelper.lockDeviceNow(this)
        }
    }

    @Suppress("DEPRECATION")
    private fun configureLockWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
            )
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}

@Composable
fun CurfewLockScreen(
    curfewManager: CurfewManager,
    securityManager: SecurityManager,
    onLockDeviceHardware: () -> Unit,
    onEmergencyCall: () -> Unit,
    onLaunchAllowedApp: (packageName: String) -> Unit,
    onCurfewEnded: () -> Unit
) {
    val config = remember { curfewManager.getCurfewConfig() }
    var remainingSeconds by remember { mutableLongStateOf(curfewManager.getRemainingSeconds()) }
    var showPinDialog by remember { mutableStateOf(false) }
    var pinError by remember { mutableStateOf<String?>(null) }

    // Live countdown timer check - Automatically unlocks when time finishes!
    LaunchedEffect(Unit) {
        while (true) {
            val isActive = curfewManager.isCurfewCurrentlyActive()
            if (!isActive) {
                // Curfew expired: Unlock phone automatically!
                onCurfewEnded()
                break
            }
            remainingSeconds = curfewManager.getRemainingSeconds()
            delay(500)
        }
    }

    val hours = remainingSeconds / 3600
    val minutes = (remainingSeconds % 3600) / 60
    val seconds = remainingSeconds % 60
    val countdownText = String.format("%02d : %02d : %02d", hours, minutes, seconds)

    val startFormatted = curfewManager.formatBanglaTimeString(config.startHour, config.startMinute)
    val endFormatted = curfewManager.formatBanglaTimeString(config.endHour, config.endMinute)

    val spiritualDhikrList = listOf(
        "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ • سُبْحَانَ اللَّهِ الْعَظِيمِ",
        "لَا إِلَٰهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ",
        "أَسْتَغْفِرُ اللَّهَ الْعَظِيمَ وَأَتُوبُ إِلَيْهِ",
        "اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ"
    )
    var currentDhikrIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(7000)
            currentDhikrIndex = (currentDhikrIndex + 1) % spiritualDhikrList.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF04070D),
                        Color(0xFF090E1A),
                        Color(0xFF04070D)
                    )
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Lock Badge Aura
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(CyberDanger.copy(alpha = 0.12f))
                    .border(2.dp, CyberDanger.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (config.isStrictUnbreakableLock) Icons.Default.LockClock else Icons.Default.Lock,
                    contentDescription = "Full Phone Lock Active",
                    tint = CyberDanger,
                    modifier = Modifier.size(46.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Headline
            Text(
                text = if (config.isStrictUnbreakableLock) "পুরো ফোন কড়া লক রয়েছে" else "পুরো ফোন কার্ফিউ লক সক্রিয়",
                color = CyberDanger,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "নির্ধারিত সময় শেষ হওয়া মাত্র পুরো ফোন স্বয়ংক্রিয়ভাবে খুলে যাবে।",
                color = CyberTextSecondary,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Big Live Digital Clock Display
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(CyberSurface)
                    .border(1.5.dp, CyberDanger.copy(alpha = 0.6f), RoundedCornerShape(18.dp))
                    .padding(vertical = 20.dp, horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.HourglassBottom,
                            contentDescription = "Countdown",
                            tint = CyberWarning,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "খুলতে আর বাকি সময়",
                            color = CyberWarning,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = countdownText,
                        color = CyberTextPrimary,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 2.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val lockPeriodText = if (config.immediateLockUntilTimestamp > 0) {
                        if (config.immediateLockTargetLabel.isNotEmpty()) config.immediateLockTargetLabel
                        else "তাৎক্ষণিক লক: নির্ধারিত সময় সমাপ্তিতে স্বয়ংক্রিয় আনলক হবে"
                    } else {
                        "লক সময়সীমা: $startFormatted থেকে $endFormatted"
                    }

                    Text(
                        text = lockPeriodText,
                        color = CyberPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Emergency Call & Whitelisted Apps Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(CyberSurface)
                    .border(1.dp, Color(0xFF10B981).copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        tint = Color(0xFF10B981),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "অনুমোদিত সংযোগ ও অ্যাপস",
                        color = Color(0xFF10B981),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "লক চলাকালীন শুধুমাত্র জরুরি ফোন কল এবং আপনার নির্বাচিত অনুমোদিত অ্যাপ চলবে। বাকি সমস্ত অ্যাপ বন্ধ রয়েছে।",
                    color = CyberTextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )

                // 1. Emergency Dial Action Button
                Button(
                    onClick = onEmergencyCall,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF059669),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().height(42.dp)
                ) {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "জরুরি ফোন কল (Emergency Call)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                // 2. Allowed Whitelisted Apps (e.g. Brilliant Connect)
                if (config.allowedAppPackages.isNotEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        config.allowedAppPackages.take(3).forEach { pkg ->
                            val appLabel = when {
                                pkg.contains("brilliant", ignoreCase = true) -> "ব্রিলিয়ান্ট অ্যাপ (Brilliant Connect)"
                                pkg.contains("dialer", ignoreCase = true) -> "সিস্টেম ফোন ডায়ালার"
                                pkg.contains("clock", ignoreCase = true) -> "ঘড়ি ও অ্যালার্ম"
                                pkg.contains("calculator", ignoreCase = true) -> "ক্যালকুলেটর"
                                else -> pkg.substringAfterLast(".").replaceFirstChar { it.uppercase() }
                            }
                            OutlinedButton(
                                onClick = { onLaunchAllowedApp(pkg) },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberPrimary),
                                border = BorderStroke(1.dp, CyberPrimary.copy(alpha = 0.6f)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth().height(38.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Apps, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "অনুমোদিত: $appLabel",
                                    fontSize = 11.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Spiritual Reflection Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(CyberSurfaceVariant.copy(alpha = 0.7f))
                    .border(1.dp, CyberSecondary.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "আত্মশুদ্ধি ও জিকির",
                        color = CyberSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = spiritualDhikrList[currentDhikrIndex],
                        color = CyberTextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Lock Screen Hardware Button (Turn Screen Off)
            Button(
                onClick = onLockDeviceHardware,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberSurfaceVariant,
                    contentColor = CyberPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PowerSettingsNew,
                        contentDescription = "Screen Lock",
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "স্ক্রিন অফ করুন (Screen Lock)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Emergency Unlock or Strict Message
            if (config.isStrictUnbreakableLock) {
                Text(
                    text = "🔒 কড়া আনব্রেকেবল লক মোড: পিন কোড দিয়েও খোলা অসম্ভব। সময় সমাপ্তির অপেক্ষায় থাকুন।",
                    color = CyberDanger,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            } else {
                OutlinedButton(
                    onClick = { showPinDialog = true },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberTextSecondary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "জরুরি আনলক (অ্যাডমিন পিন প্রয়োজন)",
                        fontSize = 11.sp
                    )
                }
            }
        }
    }

    // Emergency PIN Dialog
    if (showPinDialog) {
        PinInputDialog(
            title = "জরুরি আনলক পিন",
            subtitle = "লক সাময়িক নিষ্ক্রিয় করতে আপনার অ্যাডমিন পিন দিন",
            errorMessage = pinError,
            onDismiss = {
                showPinDialog = false
                pinError = null
            },
            onSubmit = { enteredPin ->
                if (securityManager.verifyPin(enteredPin)) {
                    curfewManager.grantEmergencyUnlock(15)
                    showPinDialog = false
                    onCurfewEnded()
                } else {
                    pinError = "ভুল পিন কোড! পুনরায় চেষ্টা করুন।"
                }
            }
        )
    }
}
