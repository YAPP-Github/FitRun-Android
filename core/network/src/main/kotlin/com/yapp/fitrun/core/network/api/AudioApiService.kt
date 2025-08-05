package com.yapp.fitrun.core.network.api

import com.yapp.fitrun.core.network.model.request.audio.DistanceFeedbackRequest
import com.yapp.fitrun.core.network.model.request.audio.PaceFeedbackRequest
import com.yapp.fitrun.core.network.model.request.audio.RunningInfoRequest
import com.yapp.fitrun.core.network.model.request.audio.TimeFeedbackRequest
import com.yapp.fitrun.core.network.model.response.AudioResponse
import com.yapp.fitrun.core.network.model.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers

interface AudioApiService {

    @GET("api/v1/audios/coach")
    @Headers(
        "Accept: audio/wav;charset=UTF-8",
        "Content-Type: audio/wav;charset=UTF-8",
    )
    suspend fun getAudioCoach(): BaseResponse<AudioResponse>

    @GET("api/v1/audios/running-info")
    @Headers(
        "Accept: audio/wav;charset=UTF-8",
        "Content-Type: audio/wav;charset=UTF-8",
    )
    suspend fun getRunningInfo(@Body runningInfoRequest: RunningInfoRequest): BaseResponse<AudioResponse>

    @GET("api/v1/audios/coach")
    @Headers(
        "Accept: audio/wav;charset=UTF-8",
        "Content-Type: audio/wav;charset=UTF-8",
    )
    suspend fun getDistanceFeedback(@Body distanceFeedbackRequest: DistanceFeedbackRequest): BaseResponse<AudioResponse>

    @GET("api/v1/audios/coach")
    @Headers(
        "Accept: audio/wav;charset=UTF-8",
        "Content-Type: audio/wav;charset=UTF-8",
    )
    suspend fun getPaceFeedback(@Body paceFeedbackRequest: PaceFeedbackRequest): BaseResponse<AudioResponse>

    @GET("api/v1/audios/coach")
    @Headers(
        "Accept: audio/wav;charset=UTF-8",
        "Content-Type: audio/wav;charset=UTF-8",
    )
    suspend fun getTimeFeedback(@Body timeFeedbackRequest: TimeFeedbackRequest): BaseResponse<AudioResponse>
}
