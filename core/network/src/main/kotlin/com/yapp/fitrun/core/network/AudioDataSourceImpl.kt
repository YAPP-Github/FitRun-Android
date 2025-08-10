package com.yapp.fitrun.core.network

import android.util.Log
import com.yapp.fitrun.core.network.api.AudioApiService
import com.yapp.fitrun.core.network.model.request.audio.DistanceFeedbackRequest
import com.yapp.fitrun.core.network.model.request.audio.PaceFeedbackRequest
import com.yapp.fitrun.core.network.model.request.audio.RunningInfoRequest
import com.yapp.fitrun.core.network.model.request.audio.TimeFeedbackRequest
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.concurrent.CancellationException
import javax.inject.Inject

class AudioDataSourceImpl @Inject constructor(
    private val service: AudioApiService,
) : AudioDataSource {

    private val TAG = "AudioDataSource"

    override suspend fun getAudioCoach(): ByteArray {
        val response = service.getAudioCoach()
        return extractAudioData(response, "getAudioCoach")
    }

    override suspend fun getRunningInfo(paceMills: String): ByteArray {
        val response = service.getRunningInfo(RunningInfoRequest(paceMills))
        return extractAudioData(response, "getRunningInfo")
    }

    override suspend fun getDistanceFeedback(type: String): ByteArray {
        val response = service.getDistanceFeedback(DistanceFeedbackRequest(type))
        return extractAudioData(response, "getDistanceFeedback")
    }

    override suspend fun getPaceFeedback(type: String): ByteArray {
        val response = service.getPaceFeedback(PaceFeedbackRequest(type))
        return extractAudioData(response, "getPaceFeedback")
    }

    override suspend fun getTimeFeedback(type: String): ByteArray {
        val response = service.getTimeFeedback(TimeFeedbackRequest(type))
        return extractAudioData(response, "getTimeFeedback")
    }

    private fun extractAudioData(response: Response<ResponseBody>, methodName: String): ByteArray {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                val audioData = body.bytes()
                Log.d(TAG, "$methodName: Successfully received audio data, size: ${audioData.size} bytes")
                return audioData
            } else {
                throw CancellationException("$methodName: Response body is null")
            }
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e(TAG, "$methodName: HTTP error ${response.code()}, message: ${response.message()}, error body: $errorBody")
            throw CancellationException("$methodName: HTTP error ${response.code()} - ${response.message()}")
        }
    }
}
