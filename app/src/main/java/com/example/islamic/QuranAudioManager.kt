package com.example.islamic

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

enum class AudioPlaybackStatus {
    IDLE,
    BUFFERING,
    PLAYING,
    PAUSED,
    ERROR
}

enum class AudioMode(val titleBangla: String, val titleEnglish: String) {
    ARABIC_ONLY("শুধু আরবি তিলাওয়াত", "Arabic Recitation"),
    WITH_BANGLA_TRANSLATION("বাংলা অর্থসহ তিলাওয়াত", "Bangla Translation & Recitation")
}

data class PlayerState(
    val status: AudioPlaybackStatus = AudioPlaybackStatus.IDLE,
    val activeSurahNumber: Int? = null,
    val activeSurahTitle: String? = null,
    val activeAudioMode: AudioMode = AudioMode.ARABIC_ONLY,
    val currentPositionMs: Int = 0,
    val totalDurationMs: Int = 0,
    val isDownloaded: Boolean = false,
    val downloadProgressPercent: Int? = null,
    val errorMessage: String? = null
)

class QuranAudioManager(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _playerState = MutableStateFlow(PlayerState())
    val playerState: StateFlow<PlayerState> = _playerState.asStateFlow()

    private val audioDir: File by lazy {
        File(context.filesDir, "quran_audio").apply { if (!exists()) mkdirs() }
    }

    fun getLocalFile(surahNumber: Int, mode: AudioMode): File {
        val suffix = if (mode == AudioMode.WITH_BANGLA_TRANSLATION) "bangla" else "arabic"
        return File(audioDir, "surah_${surahNumber}_${suffix}.mp3")
    }

    fun isSurahDownloaded(surahNumber: Int, mode: AudioMode): Boolean {
        val file = getLocalFile(surahNumber, mode)
        if (file.exists() && file.length() > 1024) return true
        // Check legacy fallback for arabic
        if (mode == AudioMode.ARABIC_ONLY) {
            val legacyFile = File(audioDir, "surah_${surahNumber}.mp3")
            return legacyFile.exists() && legacyFile.length() > 1024
        }
        return false
    }

    fun playOrPauseSurah(surah: QuranSurah, mode: AudioMode) {
        val current = _playerState.value
        if (current.activeSurahNumber == surah.number && current.activeAudioMode == mode) {
            if (current.status == AudioPlaybackStatus.PLAYING) {
                mediaPlayer?.pause()
                _playerState.value = current.copy(status = AudioPlaybackStatus.PAUSED)
                return
            } else if (current.status == AudioPlaybackStatus.PAUSED) {
                mediaPlayer?.start()
                _playerState.value = current.copy(status = AudioPlaybackStatus.PLAYING)
                return
            }
        }

        // Fresh Start
        stopPlayback()

        val isDownloaded = isSurahDownloaded(surah.number, mode)
        _playerState.value = PlayerState(
            status = AudioPlaybackStatus.BUFFERING,
            activeSurahNumber = surah.number,
            activeSurahTitle = "${surah.nameBangla} (${mode.titleBangla})",
            activeAudioMode = mode,
            isDownloaded = isDownloaded
        )

        try {
            val player = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )

                val source = if (isDownloaded) {
                    val local = getLocalFile(surah.number, mode)
                    if (local.exists() && local.length() > 1024) {
                        local.absolutePath
                    } else {
                        File(audioDir, "surah_${surah.number}.mp3").absolutePath
                    }
                } else {
                    if (mode == AudioMode.WITH_BANGLA_TRANSLATION) {
                        surah.audioBanglaUrl
                    } else {
                        surah.audioArabicUrl
                    }
                }

                setDataSource(source)

                setOnPreparedListener { mp ->
                    mp.start()
                    _playerState.value = _playerState.value.copy(
                        status = AudioPlaybackStatus.PLAYING,
                        totalDurationMs = mp.duration
                    )
                }

                setOnCompletionListener {
                    _playerState.value = _playerState.value.copy(
                        status = AudioPlaybackStatus.IDLE,
                        currentPositionMs = 0
                    )
                }

                setOnErrorListener { _, what, extra ->
                    Log.e("QuranAudioManager", "MediaPlayer error: $what, $extra")
                    _playerState.value = _playerState.value.copy(
                        status = AudioPlaybackStatus.ERROR,
                        errorMessage = "অডিও প্লে করতে সমস্যা হয়েছে। ইন্টারনেট সংযোগ চেক করুন।"
                    )
                    true
                }

                prepareAsync()
            }
            mediaPlayer = player
        } catch (e: Exception) {
            _playerState.value = _playerState.value.copy(
                status = AudioPlaybackStatus.ERROR,
                errorMessage = e.localizedMessage ?: "অডিও লোড করা যায়নি"
            )
        }
    }

    fun stopPlayback() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            // ignore
        }
        _playerState.value = _playerState.value.copy(status = AudioPlaybackStatus.IDLE)
    }

    /**
     * Downloads surah audio (Arabic or Bangla translation) for offline playback.
     */
    fun downloadSurahAudio(surah: QuranSurah, mode: AudioMode, onComplete: (Boolean) -> Unit) {
        if (isSurahDownloaded(surah.number, mode)) {
            onComplete(true)
            return
        }

        val targetUrl = if (mode == AudioMode.WITH_BANGLA_TRANSLATION) {
            surah.audioBanglaUrl
        } else {
            surah.audioArabicUrl
        }

        scope.launch {
            _playerState.value = _playerState.value.copy(
                downloadProgressPercent = 5,
                activeSurahNumber = surah.number,
                activeAudioMode = mode
            )
            val success = withContext(Dispatchers.IO) {
                try {
                    val targetFile = getLocalFile(surah.number, mode)
                    val url = URL(targetUrl)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.instanceFollowRedirects = true
                    connection.connectTimeout = 15000
                    connection.readTimeout = 20000
                    connection.connect()

                    var actualConnection = connection
                    if (connection.responseCode in 300..399) {
                        val newUrl = connection.getHeaderField("Location")
                        if (newUrl != null) {
                            val redirect = URL(newUrl).openConnection() as HttpURLConnection
                            redirect.connectTimeout = 15000
                            redirect.readTimeout = 20000
                            redirect.connect()
                            actualConnection = redirect
                        }
                    }

                    if (actualConnection.responseCode != HttpURLConnection.HTTP_OK) {
                        return@withContext false
                    }

                    val fileLength = actualConnection.contentLength
                    val input = actualConnection.inputStream
                    val output = FileOutputStream(targetFile)

                    val data = ByteArray(4096)
                    var total: Long = 0
                    var count: Int
                    while (input.read(data).also { count = it } != -1) {
                        total += count
                        if (fileLength > 0) {
                            val percent = ((total * 100) / fileLength).toInt()
                            withContext(Dispatchers.Main) {
                                _playerState.value = _playerState.value.copy(downloadProgressPercent = percent)
                            }
                        }
                        output.write(data, 0, count)
                    }

                    output.flush()
                    output.close()
                    input.close()
                    true
                } catch (e: Exception) {
                    Log.e("QuranAudioManager", "Download failed", e)
                    false
                }
            }

            _playerState.value = _playerState.value.copy(
                downloadProgressPercent = null,
                isDownloaded = isSurahDownloaded(surah.number, mode)
            )
            onComplete(success)
        }
    }

    fun deleteDownloadedSurah(surahNumber: Int, mode: AudioMode) {
        val file = getLocalFile(surahNumber, mode)
        if (file.exists()) {
            file.delete()
        }
        val legacyFile = File(audioDir, "surah_${surahNumber}.mp3")
        if (mode == AudioMode.ARABIC_ONLY && legacyFile.exists()) {
            legacyFile.delete()
        }
        if (_playerState.value.activeSurahNumber == surahNumber && _playerState.value.activeAudioMode == mode) {
            _playerState.value = _playerState.value.copy(isDownloaded = false)
        }
    }
}
