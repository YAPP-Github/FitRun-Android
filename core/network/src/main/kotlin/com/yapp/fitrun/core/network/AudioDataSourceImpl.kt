package com.yapp.fitrun.core.network

import com.yapp.fitrun.core.network.api.AudioApiService
import com.yapp.fitrun.core.network.model.request.audio.DistanceFeedbackRequest
import com.yapp.fitrun.core.network.model.request.audio.PaceFeedbackRequest
import com.yapp.fitrun.core.network.model.request.audio.RunningInfoRequest
import com.yapp.fitrun.core.network.model.request.audio.TimeFeedbackRequest
import com.yapp.fitrun.core.network.model.response.AudioResponse
import java.util.concurrent.CancellationException
import javax.inject.Inject

class AudioDataSourceImpl @Inject constructor(
    private val service: AudioApiService,
) : AudioDataSource {
    override suspend fun getAudioCoach(): AudioResponse {
        val response = service.getAudioCoach()

        if (response.code == "SUCCESS") {
            return response.result
        } else {
            throw CancellationException(response.code)
        }
    }

    override suspend fun getRunningInfo(runningInfoRequest: RunningInfoRequest): AudioResponse {
        val response = service.getRunningInfo(runningInfoRequest)

        if (response.code == "SUCCESS") {
            return response.result
        } else {
            throw CancellationException(response.code)
        }
    }

    override suspend fun getDistanceFeedback(distanceFeedbackRequest: DistanceFeedbackRequest): AudioResponse {
        val response = service.getDistanceFeedback(distanceFeedbackRequest)

        if (response.code == "SUCCESS") {
            return response.result
        } else {
            throw CancellationException(response.code)
        }
    }

    override suspend fun getPaceFeedback(paceFeedbackRequest: PaceFeedbackRequest): AudioResponse {
        val response = service.getPaceFeedback(paceFeedbackRequest)

        if (response.code == "SUCCESS") {
            return response.result
        } else {
            throw CancellationException(response.code)
        }
    }

    override suspend fun getTimeFeedback(timeFeedbackRequest: TimeFeedbackRequest): AudioResponse {
        val response = service.getTimeFeedback(timeFeedbackRequest)

        if (response.code == "SUCCESS") {
            return response.result
        } else {
            throw CancellationException(response.code)
        }
    }
}
