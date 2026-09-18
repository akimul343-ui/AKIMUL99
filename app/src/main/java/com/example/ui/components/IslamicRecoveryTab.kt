package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.islamic.AudioMode
import com.example.islamic.AudioPlaybackStatus
import com.example.islamic.HadithBook
import com.example.islamic.HadithItem
import com.example.islamic.IslamicAdvice
import com.example.islamic.IslamicPoem
import com.example.islamic.PlayerState
import com.example.islamic.QuranSurah
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

@Composable
fun IslamicRecoveryTab(
    surahs: List<QuranSurah>,
    advices: List<IslamicAdvice>,
    hadithBooks: List<HadithBook>,
    islamicPoems: List<IslamicPoem>,
    playerState: PlayerState,
    selectedAudioMode: AudioMode,
    onSetAudioMode: (AudioMode) -> Unit,
    onPlaySurah: (QuranSurah, AudioMode) -> Unit,
    onDownloadSurah: (QuranSurah, AudioMode) -> Unit,
    onDeleteSurah: (Int, AudioMode) -> Unit
) {
    var selectedCategoryTab by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredSurahs = remember(searchQuery, surahs) {
        if (searchQuery.isBlank()) surahs
        else surahs.filter {
            it.nameBangla.contains(searchQuery, ignoreCase = true) ||
            it.nameEnglish.contains(searchQuery, ignoreCase = true) ||
            it.nameArabic.contains(searchQuery, ignoreCase = true) ||
            it.number.toString() == searchQuery.trim()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Islamic Guidance Header
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF0D3325),
                                CyberSurface
                            )
                        )
                    )
                    .border(1.dp, CyberSecondary.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                    .padding(18.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(CyberSecondary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = "Quran",
                            tint = CyberSecondary,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "পবিত্র জীবন ও অন্তরের খোরাক",
                        color = CyberTextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "أَلَا بِذِكْرِ اللَّهِ تَطْمَئِنُّ الْقُلُوبُ\n\"জেনে রেখো, আল্লাহর স্মরণেই কেবল হৃদয় প্রশান্ত হয়।\" (সূরা আর-রাদ: ২৮)",
                        color = CyberSecondary,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 17.sp
                    )
                }
            }
        }

        // Active Audio Player Floating Card
        if (playerState.activeSurahNumber != null) {
            item {
                ActiveAudioPlayerCard(
                    playerState = playerState,
                    onTogglePlay = {
                        val active = surahs.firstOrNull { it.number == playerState.activeSurahNumber }
                        if (active != null) onPlaySurah(active, playerState.activeAudioMode)
                    }
                )
            }
        }

        // Main Navigation Categories (কুরআন ১১৪ সূরা, হাদিস গ্রন্থ, কবিতা ও নাশীদ, আত্মশুদ্ধি)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(CyberSurface)
                    .border(1.dp, CyberBorder, RoundedCornerShape(12.dp))
                    .padding(4.dp)
            ) {
                CategoryTabButton(
                    label = "📖 কুরআন",
                    isSelected = selectedCategoryTab == 0,
                    modifier = Modifier.weight(1f),
                    onClick = { selectedCategoryTab = 0 }
                )
                CategoryTabButton(
                    label = "📜 হাদিস",
                    isSelected = selectedCategoryTab == 1,
                    modifier = Modifier.weight(1f),
                    onClick = { selectedCategoryTab = 1 }
                )
                CategoryTabButton(
                    label = "✍️ কবিতা",
                    isSelected = selectedCategoryTab == 2,
                    modifier = Modifier.weight(1f),
                    onClick = { selectedCategoryTab = 2 }
                )
                CategoryTabButton(
                    label = "🛡️ আমল",
                    isSelected = selectedCategoryTab == 3,
                    modifier = Modifier.weight(1f),
                    onClick = { selectedCategoryTab = 3 }
                )
            }
        }

        when (selectedCategoryTab) {
            0 -> {
                // ================= Quran Section =================
                // Dual-Mode Audio Toggle Option (User requested: অর্থসহ বলবে এবং শুধু সূরা বলবে দুইটা অপশন)
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(CyberSurfaceVariant)
                            .border(1.dp, CyberBorder, RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Headphones,
                                    contentDescription = null,
                                    tint = CyberPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "অডিও প্লেব্যাক ও ডাউনলোড মোড নির্বাচন করুন:",
                                    color = CyberTextPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                AudioModePill(
                                    title = "শুধু তিলাওয়াত",
                                    subtitle = "বিশুদ্ধ আরবি ক্বিরাআত",
                                    isSelected = selectedAudioMode == AudioMode.ARABIC_ONLY,
                                    modifier = Modifier.weight(1f),
                                    onClick = { onSetAudioMode(AudioMode.ARABIC_ONLY) }
                                )

                                AudioModePill(
                                    title = "অর্থসহ তিলাওয়াত",
                                    subtitle = "বাংলা অনুবাদসহ তিলাওয়াত",
                                    isSelected = selectedAudioMode == AudioMode.WITH_BANGLA_TRANSLATION,
                                    modifier = Modifier.weight(1f),
                                    onClick = { onSetAudioMode(AudioMode.WITH_BANGLA_TRANSLATION) }
                                )
                            }
                        }
                    }
                }

                // Search Bar for 114 Surahs
                item {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("সূরা খুঁজুন (নাম বা নম্বর দিয়ে...)", fontSize = 12.sp, color = CyberTextMuted) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = CyberPrimary)
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyberPrimary,
                            unfocusedBorderColor = CyberBorder,
                            focusedTextColor = CyberTextPrimary,
                            unfocusedTextColor = CyberTextPrimary
                        ),
                        singleLine = true
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "মোট ১১৪টি সূরা (${filteredSurahs.size}টি প্রদর্শিত)",
                            color = CyberTextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (selectedAudioMode == AudioMode.WITH_BANGLA_TRANSLATION) "🎧 বাংলা অর্থসহ মোড সক্রিয়" else "🎧 শুধু আরবি মোড সক্রিয়",
                            color = CyberSecondary,
                            fontSize = 11.sp
                        )
                    }
                }

                items(filteredSurahs, key = { it.number }) { surah ->
                    SurahItemCard(
                        surah = surah,
                        playerState = playerState,
                        currentAudioMode = selectedAudioMode,
                        onPlay = { onPlaySurah(surah, selectedAudioMode) },
                        onDownload = { onDownloadSurah(surah, selectedAudioMode) },
                        onDelete = { onDeleteSurah(surah.number, selectedAudioMode) }
                    )
                }
            }

            1 -> {
                // ================= Hadith Books Section =================
                item {
                    Text(
                        text = "বিশুদ্ধ হাদিস গ্রন্থ সংকলন",
                        color = CyberTextSecondary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(hadithBooks, key = { it.id }) { book ->
                    HadithBookCard(book = book)
                }
            }

            2 -> {
                // ================= Islamic Poetry & Nasheed Section =================
                item {
                    Text(
                        text = "আত্মশুদ্ধি ও হৃদয়ের খোরাক: ইসলামিক কবিতা ও নাশীদ",
                        color = CyberTextSecondary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(islamicPoems, key = { it.id }) { poem ->
                    IslamicPoemCard(poem = poem)
                }
            }

            3 -> {
                // ================= Anti-Addiction Practical Guidelines =================
                item {
                    Text(
                        text = "কু-প্রবৃত্তি ও আসক্তি থেকে বাঁচার ইসলামিক মূলনীতি",
                        color = CyberTextSecondary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(advices, key = { it.id }) { advice ->
                    IslamicAdviceCard(advice = advice)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CategoryTabButton(
    label: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) CyberPrimary.copy(alpha = 0.2f) else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (isSelected) CyberPrimary else CyberTextMuted,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun AudioModePill(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) CyberSecondary.copy(alpha = 0.2f) else CyberSurface)
            .border(
                1.dp,
                if (isSelected) CyberSecondary else CyberBorder,
                RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = CyberSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(
                    text = title,
                    color = if (isSelected) CyberSecondary else CyberTextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = subtitle,
                color = CyberTextMuted,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun ActiveAudioPlayerCard(
    playerState: PlayerState,
    onTogglePlay: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CyberSurfaceVariant)
            .border(1.dp, CyberPrimary, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(CyberPrimary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = onTogglePlay) {
                            Icon(
                                imageVector = if (playerState.status == AudioPlaybackStatus.PLAYING) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Play/Pause",
                                tint = CyberPrimary
                            )
                        }
                    }

                    Column {
                        Text(
                            text = playerState.activeSurahTitle ?: "সূরা পাঠ",
                            color = CyberTextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = when (playerState.status) {
                                AudioPlaybackStatus.BUFFERING -> "অডিও লোড হচ্ছে..."
                                AudioPlaybackStatus.PLAYING -> "সরাসরি প্লে হচ্ছে"
                                AudioPlaybackStatus.PAUSED -> "তিলাওয়াত স্থগিত"
                                AudioPlaybackStatus.ERROR -> (playerState.errorMessage ?: "এরর")
                                else -> "প্রস্তুত"
                            },
                            color = if (playerState.status == AudioPlaybackStatus.ERROR) CyberDanger else CyberPrimary,
                            fontSize = 11.sp
                        )
                    }
                }

                if (playerState.isDownloaded) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(CyberSecondary.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "অফলাইন",
                            color = CyberSecondary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            if (playerState.downloadProgressPercent != null) {
                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(
                    progress = { (playerState.downloadProgressPercent.toFloat() / 100f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = CyberPrimary,
                    trackColor = CyberBorder
                )
                Text(
                    text = "ডাউনলোড হচ্ছে: ${playerState.downloadProgressPercent}%",
                    color = CyberTextMuted,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun SurahItemCard(
    surah: QuranSurah,
    playerState: PlayerState,
    currentAudioMode: AudioMode,
    onPlay: () -> Unit,
    onDownload: () -> Unit,
    onDelete: () -> Unit
) {
    val isPlayingThis = playerState.activeSurahNumber == surah.number &&
            playerState.activeAudioMode == currentAudioMode &&
            playerState.status == AudioPlaybackStatus.PLAYING

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CyberSurface)
            .border(
                1.dp,
                if (isPlayingThis) CyberPrimary else CyberBorder,
                RoundedCornerShape(16.dp)
            )
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(CyberPrimary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${surah.number}",
                            color = CyberPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }

                    Column {
                        Text(
                            text = surah.nameBangla,
                            color = CyberTextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${surah.revelationType} • ${surah.versesCount} আয়াত • ${surah.nameEnglish}",
                            color = CyberTextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                Text(
                    text = surah.nameArabic,
                    color = CyberSecondary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = surah.banglaSummary,
                color = CyberTextSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )

            // Action Buttons (Listen & Download in chosen mode)
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onPlay,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isPlayingThis) CyberWarning else CyberPrimary,
                        contentColor = CyberBackground
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = if (isPlayingThis) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isPlayingThis) "থামুন" else if (currentAudioMode == AudioMode.WITH_BANGLA_TRANSLATION) "অর্থসহ শুনুন" else "তিলাওয়াত শুনুন",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    onClick = onDownload,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberSecondary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Download",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "ডাউনলোড", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun HadithBookCard(book: HadithBook) {
    var isExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CyberSurface)
            .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(CyberSecondary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoStories,
                            contentDescription = null,
                            tint = CyberSecondary,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Column {
                        Text(
                            text = book.titleBangla,
                            color = CyberTextPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = book.author,
                            color = CyberTextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                Text(
                    text = book.titleArabic,
                    color = CyberSecondary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = book.description,
                color = CyberTextSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )

            // Button to toggle hadiths inside book
            Button(
                onClick = { isExpanded = !isExpanded },
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberSurfaceVariant,
                    contentColor = CyberPrimary
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isExpanded) "সংক্ষিপ্ত করুন ▲" else "নির্বাচিত হাদিসসমূহ পড়ুন (${book.hadiths.size}টি) ▼",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    book.hadiths.forEach { item ->
                        HadithItemCard(hadith = item)
                    }
                }
            }
        }
    }
}

@Composable
fun HadithItemCard(hadith: HadithItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CyberSurfaceVariant)
            .border(1.dp, CyberBorder.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${hadith.bookName} • ${hadith.hadithNumber}",
                    color = CyberPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "বর্ণনাকারী: ${hadith.narrator}",
                    color = CyberTextMuted,
                    fontSize = 10.sp
                )
            }

            Text(
                text = hadith.arabicText,
                color = CyberTextPrimary,
                fontSize = 14.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                lineHeight = 20.sp
            )

            Text(
                text = "অর্থ: \"${hadith.banglaTranslation}\"",
                color = CyberTextSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(CyberSecondary.copy(alpha = 0.12f))
                    .padding(8.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = CyberSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "আমলি শিক্ষা: ${hadith.significance}",
                        color = CyberSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun IslamicPoemCard(poem: IslamicPoem) {
    var isExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CyberSurface)
            .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(CyberWarning.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MusicNote,
                            contentDescription = null,
                            tint = CyberWarning,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = poem.title,
                            color = CyberTextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "কবি: ${poem.poet} • সুর: ${poem.theme}",
                            color = CyberTextMuted,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // Preview first stanza
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(CyberSurfaceVariant)
                    .padding(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = poem.stanzas.first(),
                        color = CyberTextPrimary,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                    if (isExpanded && poem.stanzas.size > 1) {
                        poem.stanzas.drop(1).forEach { stanza ->
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = stanza,
                                color = CyberTextPrimary,
                                fontSize = 12.sp,
                                lineHeight = 18.sp,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "💡 ${poem.spiritualReflection}",
                    color = CyberSecondary,
                    fontSize = 11.sp,
                    modifier = Modifier.weight(1f)
                )

                if (poem.stanzas.size > 1) {
                    OutlinedButton(
                        onClick = { isExpanded = !isExpanded },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberPrimary)
                    ) {
                        Text(text = if (isExpanded) "কম দেখুন" else "সম্পূর্ণ কবিতা", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun IslamicAdviceCard(advice: IslamicAdvice) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CyberSurface)
            .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(CyberSecondary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = CyberSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column {
                    Text(
                        text = advice.titleBangla,
                        color = CyberTextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = advice.reference,
                        color = CyberTextMuted,
                        fontSize = 10.sp
                    )
                }
            }

            if (advice.arabicText != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(CyberSurfaceVariant)
                        .padding(10.dp)
                ) {
                    Text(
                        text = advice.arabicText,
                        color = CyberPrimary,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Text(
                text = advice.banglaTranslation,
                color = CyberTextSecondary,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(CyberSecondary.copy(alpha = 0.1f))
                    .padding(10.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = CyberSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "বাস্তব আমল: ${advice.practicalTip}",
                        color = CyberSecondary,
                        fontSize = 11.sp,
                        lineHeight = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
