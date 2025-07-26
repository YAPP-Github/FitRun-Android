package com.yapp.fitrun.core.network.api

import com.yapp.fitrun.core.network.model.request.RunningPurposeRequest
import com.yapp.fitrun.core.network.model.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface GoalApiService {
    @POST("/api/v1/users/goals/purpose")
    suspend fun setRunningPurpose(@Body runningPurposeRequest: RunningPurposeRequest): BaseResponse<Unit>

    @PATCH("/api/v1/users/goals/purpose")
    suspend fun updateRunningPurpose(@Body runningPurposeRequest: RunningPurposeRequest)
}
