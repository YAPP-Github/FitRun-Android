package com.yapp.fitrun.core.network

import com.yapp.fitrun.core.network.api.GoalApiService
import com.yapp.fitrun.core.network.model.request.RunningPurposeRequest
import com.yapp.fitrun.core.network.model.response.BaseResponse
import javax.inject.Inject

class GoalDatasourceImpl @Inject constructor(
    private val service: GoalApiService
): GoalDatasource {
    override suspend fun setRunningPurpose(purpose: RunningPurposeRequest): BaseResponse<Unit> {
         return service.setRunningPurpose(purpose)
    }

    override suspend fun updateRunningPurpose(purpose: RunningPurposeRequest) {
        service.updateRunningPurpose(purpose)
    }
}