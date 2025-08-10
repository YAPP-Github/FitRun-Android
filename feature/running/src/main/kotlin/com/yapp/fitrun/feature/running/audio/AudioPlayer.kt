package com.yapp.fitrun.feature.running.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.media.MediaPlayer
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.CancellationException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioPlayer @Inject constructor() {

    private var mediaPlayer: MediaPlayer? = null
    private var audioTrack: AudioTrack? = null
    private val TAG = "AudioPlayer"

    // MediaPlayer 사용 (비동기 방식으로 개선)
    suspend fun playWithMediaPlayer(audioData: ByteArray): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Starting audio playback, data size: ${audioData.size}")

            // 이전 재생 안전하게 중지
            stopPlaying()

            // WAV 파일 검증
            if (!isValidWavFile(audioData)) {
                Log.e(TAG, "Invalid WAV file format")
                return@withContext Result.failure(Exception("Invalid WAV file format"))
            }

            // 임시 파일 생성
            val tempFile = File.createTempFile("audio_coach", ".wav")
            tempFile.deleteOnExit()

            FileOutputStream(tempFile).use { fos ->
                fos.write(audioData)
            }

            // Main 스레드에서 MediaPlayer 설정
            withContext(Dispatchers.Main) {
                try {
                    mediaPlayer = MediaPlayer().apply {
                        setAudioAttributes(
                            AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build(),
                        )

                        setDataSource(tempFile.absolutePath)

                        setOnCompletionListener {
                            Log.d(TAG, "Audio playback completed")
                            tempFile.delete()
                            releaseMediaPlayer()
                        }

                        setOnErrorListener { mp, what, extra ->
                            Log.e(TAG, "MediaPlayer error: what=$what, extra=$extra")
                            tempFile.delete()
                            releaseMediaPlayer()
                            true
                        }

                        // 동기적으로 준비하고 재생
                        prepare()
                        start()
                        Log.d(TAG, "MediaPlayer started successfully")
                    }
                } catch (e: CancellationException) {
                    Log.e(TAG, "Error setting up MediaPlayer", e)
                    tempFile.delete()
                    releaseMediaPlayer()
                    throw e
                }
            }

            Result.success(Unit)
        } catch (e: CancellationException) {
            Log.e(TAG, "Failed to play audio", e)
            Result.failure(e)
        }
    }

    // AudioTrack 사용 (WAV 헤더 파싱 필요)
    fun playWithAudioTrack(audioData: ByteArray): Result<Unit> {
        return try {
            // 이전 재생 안전하게 중지
            stopPlaying()

            // WAV 헤더 파싱 (44바이트 스킵)
            val audioDataWithoutHeader = if (audioData.size > 44) {
                audioData.sliceArray(44 until audioData.size)
            } else {
                audioData
            }

            // AudioTrack 설정
            val sampleRate = 44100 // 일반적인 WAV 샘플레이트
            val channelConfig = AudioFormat.CHANNEL_OUT_MONO
            val audioFormat = AudioFormat.ENCODING_PCM_16BIT
            val bufferSize = AudioTrack.getMinBufferSize(sampleRate, channelConfig, audioFormat)

            audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build(),
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setSampleRate(sampleRate)
                        .setEncoding(audioFormat)
                        .setChannelMask(channelConfig)
                        .build(),
                )
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack?.apply {
                write(audioDataWithoutHeader, 0, audioDataWithoutHeader.size)
                play()
            }

            Result.success(Unit)
        } catch (e: CancellationException) {
            Log.e(TAG, "Failed to play audio with AudioTrack", e)
            Result.failure(e)
        }
    }

    fun stopPlaying() {
        Log.d(TAG, "Stopping audio playback")

        // MediaPlayer 안전하게 해제
        releaseMediaPlayer()

        // AudioTrack 안전하게 해제
        releaseAudioTrack()
    }

    fun releaseMediaPlayer() {
        // 1. mediaPlayer가 null이면 바로 반환
        val player = mediaPlayer ?: return

        // 2. 재생 중이면 stop() — IllegalStateException만 처리
        runCatching {
            if (player.isPlaying) player.stop()
        }.onFailure { e ->
            if (e is IllegalStateException) {
                Log.w(TAG, "MediaPlayer already in invalid state", e)
            } else {
                Log.e(TAG, "Unexpected error stopping MediaPlayer", e)
            }
        }

        // 3. reset() — 문제 생겨도 로그만 남김
        runCatching { player.reset() }
            .onFailure { Log.w(TAG, "Error resetting MediaPlayer", it) }

        // 4. release() — 문제 생겨도 로그만 남김
        runCatching { player.release() }
            .onFailure { Log.w(TAG, "Error releasing MediaPlayer", it) }

        // 5. 참조 해제
        mediaPlayer = null
    }

    private fun releaseAudioTrack() {
        try {
            audioTrack?.let { track ->
                try {
                    track.stop()
                } catch (e: CancellationException) {
                    Log.w(TAG, "Error stopping AudioTrack", e)
                }

                try {
                    track.release()
                } catch (e: CancellationException) {
                    Log.w(TAG, "Error releasing AudioTrack", e)
                }
            }
        } catch (e: CancellationException) {
            Log.e(TAG, "Error in releaseAudioTrack", e)
        } finally {
            audioTrack = null
        }
    }

    fun isPlaying(): Boolean {
        return try {
            mediaPlayer?.isPlaying == true
        } catch (e: IllegalStateException) {
            Log.w(TAG, "MediaPlayer in illegal state when checking isPlaying $e")
            false
        } || try {
            audioTrack?.playState == AudioTrack.PLAYSTATE_PLAYING
        } catch (e: CancellationException) {
            Log.d(TAG, "isPlaying: $e")
            false
        }
    }

    private fun isValidWavFile(audioData: ByteArray): Boolean {
        if (audioData.size < 44) {
            Log.e(TAG, "Audio data too small: ${audioData.size} bytes")
            return false
        }

        // WAV 파일 시그니처 확인 (RIFF)
        val isValid = audioData[0] == 'R'.code.toByte() && audioData[1] == 'I'.code.toByte() && audioData[2] == 'F'.code.toByte() && audioData[3] == 'F'.code.toByte()

        if (!isValid) {
            Log.e(TAG, "Invalid WAV signature: ${audioData.take(4).map { it.toInt() and 0xFF }}")
        }

        return isValid
    }
}
