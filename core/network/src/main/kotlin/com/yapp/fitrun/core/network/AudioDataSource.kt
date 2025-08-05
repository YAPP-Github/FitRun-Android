package com.yapp.fitrun.core.network

import com.yapp.fitrun.core.network.model.request.audio.DistanceFeedbackRequest
import com.yapp.fitrun.core.network.model.request.audio.PaceFeedbackRequest
import com.yapp.fitrun.core.network.model.request.audio.RunningInfoRequest
import com.yapp.fitrun.core.network.model.request.audio.TimeFeedbackRequest
import com.yapp.fitrun.core.network.model.response.AudioResponse

interface AudioDataSource {

    suspend fun getAudioCoach(): AudioResponse

    suspend fun getRunningInfo(runningInfoRequest: RunningInfoRequest): AudioResponse

    suspend fun getDistanceFeedback(distanceFeedbackRequest: DistanceFeedbackRequest): AudioResponse

    suspend fun getPaceFeedback(paceFeedbackRequest: PaceFeedbackRequest): AudioResponse

    suspend fun getTimeFeedback(timeFeedbackRequest: TimeFeedbackRequest): AudioResponse
}
