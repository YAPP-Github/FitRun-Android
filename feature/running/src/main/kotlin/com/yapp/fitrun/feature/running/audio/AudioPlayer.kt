package com.yapp.fitrun.feature.running.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.media.MediaPlayer
import android.util.Log
import kotlinx.coroutines.CancellationException
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class AudioPlayer @Inject constructor() {

    private var mediaPlayer: MediaPlayer? = null
    private var audioTrack: AudioTrack? = null

    // 방법 1: MediaPlayer 사용 (임시 파일 생성)
    suspend fun playWithMediaPlayer(audioData: ByteArray): Result<Unit> = suspendCoroutine { continuation ->
        try {
            // 이전 재생 중지
            stopPlaying()

            // 임시 파일 생성
            val tempFile = File.createTempFile("audio_coach", ".wav")
            tempFile.deleteOnExit()

            FileOutputStream(tempFile).use { fos ->
                fos.write(audioData)
            }

            // MediaPlayer 설정 및 재생
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build(),
                )
                setDataSource(tempFile.absolutePath)
                setOnCompletionListener {
                    tempFile.delete()
                    release()
                    continuation.resume(Result.success(Unit))
                }
                setOnErrorListener { _, what, extra ->
                    Log.e("AudioCoachPlayer", "MediaPlayer error: what=$what, extra=$extra")
                    tempFile.delete()
                    release()
                    continuation.resume(Result.failure(Exception("MediaPlayer error: $what")))
                    true
                }
                prepare()
                start()
            }
        } catch (e: CancellationException) {
            Log.e("AudioCoachPlayer", "Failed to play audio", e)
            continuation.resume(Result.failure(e))
        }
    }

    // 방법 2: AudioTrack 사용 (WAV 헤더 파싱 필요)
    fun playWithAudioTrack(audioData: ByteArray): Result<Unit> {
        return try {
            // 이전 재생 중지
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
            Log.e("AudioCoachPlayer", "Failed to play audio with AudioTrack", e)
            Result.failure(e)
        }
    }

    fun stopPlaying() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null

        audioTrack?.apply {
            stop()
            release()
        }
        audioTrack = null
    }

    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying == true || audioTrack?.playState == AudioTrack.PLAYSTATE_PLAYING
    }
}
