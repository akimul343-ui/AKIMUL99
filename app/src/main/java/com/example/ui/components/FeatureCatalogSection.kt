package com.example.ui.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.SafeGuardTab
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberDanger
import com.example.ui.theme.CyberPrimary
import com.example.ui.theme.CyberSecondary
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextPrimary
import com.example.ui.theme.CyberTextSecondary
import com.example.ui.theme.CyberWarning
import com.example.util.AppStrings

@Composable
fun FeatureCatalogSection(
    isBangla: Boolean,
    onNavigateTab: (SafeGuardTab) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = AppStrings.t("feature_hub_title", isBangla),
                color = CyberTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = AppStrings.t("feature_hub_desc", isBangla),
                color = CyberTextSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        }

        // 1. Bedtime Curfew Lock Card (Phone Lock & Schedule)
        FeatureLaunchCard(
            title = AppStrings.t("feat_curfew_title", isBangla),
            description = AppStrings.t("feat_curfew_desc", isBangla),
            badge = if (isBangla) "ফোন লক ও কার্ফিউ" else "Phone Lockdown",
            badgeColor = CyberWarning,
            icon = Icons.Default.Bedtime,
            iconColor = CyberWarning,
            onClick = { onNavigateTab(SafeGuardTab.BEDTIME_LOCK) }
        )

        // 2. Holy Quran & Audio Player Card
        FeatureLaunchCard(
            title = AppStrings.t("feat_quran_title", isBangla),
            description = AppStrings.t("feat_quran_desc", isBangla),
            badge = if (isBangla) "অডিও ও অর্থ" else "Audio Player",
            badgeColor = CyberSecondary,
            icon = Icons.Default.MenuBook,
            iconColor = CyberSecondary,
            onClick = { onNavigateTab(SafeGuardTab.ISLAMIC_RECOVERY) }
        )

        // 3. Anti-Addiction Islamic Guidance Card
        FeatureLaunchCard(
            title = AppStrings.t("feat_recovery_title", isBangla),
            description = AppStrings.t("feat_recovery_desc", isBangla),
            badge = if (isBangla) "আত্মশুদ্ধি ও আমল" else "Recovery",
            badgeColor = Color(0xFF10B981),
            icon = Icons.Default.VolunteerActivism,
            iconColor = Color(0xFF10B981),
            onClick = { onNavigateTab(SafeGuardTab.ISLAMIC_RECOVERY) }
        )

        // 4. Anti-Uninstall Tamper Protection Card
        FeatureLaunchCard(
            title = AppStrings.t("feat_anti_uninstall_title", isBangla),
            description = AppStrings.t("feat_anti_uninstall_desc", isBangla),
            badge = if (isBangla) "ডিভাইস অ্যাডমিন" else "Admin Lock",
            badgeColor = CyberDanger,
            icon = Icons.Default.Lock,
            iconColor = CyberDanger,
            onClick = { onNavigateTab(SafeGuardTab.ANTI_UNINSTALL) }
        )

        // 5. Real-time Activity Logs Card
        FeatureLaunchCard(
            title = AppStrings.t("feat_logs_title", isBangla),
            description = AppStrings.t("feat_logs_desc", isBangla),
            badge = if (isBangla) "নিরাপত্তা ও লক লগ" else "Security Logs",
            badgeColor = CyberPrimary,
            icon = Icons.Default.History,
            iconColor = CyberPrimary,
            onClick = { onNavigateTab(SafeGuardTab.ACTIVITY_LOGS) }
        )

        // 6. Render Cloud Remote Admin Card
        FeatureLaunchCard(
            title = if (isBangla) "রেন্ডার ক্লাউড অ্যাডমিন (Render.com)" else "Render Cloud Admin",
            description = if (isBangla)
                "Render.com ব্যবহার করে যেকোনো স্থান থেকে ফোন লক, আনলক ও রিমোট কনফিগারেশন পরিচালনা করুন।"
            else
                "Manage and control your phone lockdown remotely using your custom Render web service.",
            badge = if (isBangla) "রিমোট কন্ট্রোল" else "Cloud Remote",
            badgeColor = CyberPrimary,
            icon = Icons.Default.CloudSync,
            iconColor = CyberPrimary,
            onClick = { onNavigateTab(SafeGuardTab.RENDER_ADMIN) }
        )
    }
}

@Composable
fun FeatureLaunchCard(
    title: String,
    description: String,
    badge: String,
    badgeColor: Color,
    icon: ImageVector,
    iconColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CyberSurface)
            .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = title,
                        color = CyberTextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(badgeColor.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = badge,
                            color = badgeColor,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = description,
                    color = CyberTextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Open",
                tint = CyberTextMuted,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
