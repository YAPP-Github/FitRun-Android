package com.yapp.fitrun.core.network

import com.yapp.fitrun.core.network.model.request.RunningPurposeRequest
import com.yapp.fitrun.core.network.model.response.BaseResponse

interface GoalDataSource {
    suspend fun setRunningPurpose(purpose: RunningPurposeRequest): BaseResponse<Unit>
    suspend fun updateRunningPurpose(purpose: RunningPurposeRequest)
}