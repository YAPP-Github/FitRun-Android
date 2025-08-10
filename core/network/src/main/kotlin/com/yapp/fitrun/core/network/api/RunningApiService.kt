package com.yapp.fitrun.core.network.api

import com.yapp.fitrun.core.network.model.request.running.RunningCompleteRequest
import com.yapp.fitrun.core.network.model.request.running.RunningStartRequest
import com.yapp.fitrun.core.network.model.response.BaseResponse
import com.yapp.fitrun.core.network.model.response.running.RunningCompleteResponse
import com.yapp.fitrun.core.network.model.response.running.RunningStartResponse
import com.yapp.fitrun.core.network.model.response.running.RunningUploadImageResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface RunningApiService {

    @POST("/api/v1/running")
    suspend fun setRunningStart(@Body runningStartRequest: RunningStartRequest): BaseResponse<RunningStartResponse>

    @POST("/api/v1/running/{recordId}")
    suspend fun setRunningComplete(
        @Path("recordId") recordId: Int,
        @Body runningCompleteRequest: RunningCompleteRequest
    ): BaseResponse<RunningCompleteResponse>

    @POST("/api/v1/running/{recordId}/images")
    suspend fun setRunningImageUpload(
        @Path("recordId") recordId: Int,
    ): BaseResponse<RunningUploadImageResponse>

}
