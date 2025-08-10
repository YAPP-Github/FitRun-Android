package com.yapp.fitrun.core.network.api

import com.yapp.fitrun.core.network.model.request.audio.DistanceFeedbackRequest
import com.yapp.fitrun.core.network.model.request.audio.PaceFeedbackRequest
import com.yapp.fitrun.core.network.model.request.audio.RunningInfoRequest
import com.yapp.fitrun.core.network.model.request.audio.TimeFeedbackRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers

interface AudioApiService {

    @GET("api/v1/audios/coach")
    @Headers(
        "Accept: audio/wav;charset=UTF-8",
        "Content-Type: audio/wav;charset=UTF-8",
    )
    suspend fun getAudioCoach(): Response<ResponseBody>

    @GET("api/v1/audios/running-info")
    @Headers(
        "Accept: audio/wav;charset=UTF-8",
        "Content-Type: audio/wav;charset=UTF-8",
    )
    suspend fun getRunningInfo(@Body runningInfoRequest: RunningInfoRequest): Response<ResponseBody>

    @GET("api/v1/audios/coach")
    @Headers(
        "Accept: audio/wav;charset=UTF-8",
        "Content-Type: audio/wav;charset=UTF-8",
    )
    suspend fun getDistanceFeedback(@Body distanceFeedbackRequest: DistanceFeedbackRequest): Response<ResponseBody>

    @GET("api/v1/audios/coach")
    @Headers(
        "Accept: audio/wav;charset=UTF-8",
        "Content-Type: audio/wav;charset=UTF-8",
    )
    suspend fun getPaceFeedback(@Body paceFeedbackRequest: PaceFeedbackRequest): Response<ResponseBody>

    @GET("api/v1/audios/coach")
    @Headers(
        "Accept: audio/wav;charset=UTF-8",
        "Content-Type: audio/wav;charset=UTF-8",
    )
    suspend fun getTimeFeedback(@Body timeFeedbackRequest: TimeFeedbackRequest): Response<ResponseBody>
}
