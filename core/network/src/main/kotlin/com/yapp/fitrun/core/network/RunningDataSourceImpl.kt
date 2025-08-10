package com.yapp.fitrun.core.network

import android.accounts.NetworkErrorException
import com.yapp.fitrun.core.network.api.RunningApiService
import com.yapp.fitrun.core.network.model.request.running.RunningCompleteRequest
import com.yapp.fitrun.core.network.model.request.running.RunningStartRequest
import com.yapp.fitrun.core.network.model.response.running.RunningCompleteResponse
import com.yapp.fitrun.core.network.model.response.running.RunningStartResponse
import com.yapp.fitrun.core.network.model.response.running.RunningUploadImageResponse
import javax.inject.Inject

class RunningDataSourceImpl @Inject constructor(
    private val service: RunningApiService
) : RunningDataSource {
    override suspend fun setRunningStart(runningStartRequest: RunningStartRequest): RunningStartResponse {
        val response = service.setRunningStart(runningStartRequest)

        if (response.code == "SUCCESS") {
            return response.result
        } else {
            throw NetworkErrorException(response.code)
        }
    }

    override suspend fun setRunningComplete(
        recordId: Int,
        runningCompleteRequest: RunningCompleteRequest
    ): RunningCompleteResponse {
        val response = service.setRunningComplete(recordId, runningCompleteRequest)

        if (response.code == "SUCCESS") {
            return response.result
        } else {
            throw NetworkErrorException(response.code)
        }
    }

    override suspend fun setRunningUploadImage(recordId: Int): RunningUploadImageResponse {
        val response = service.setRunningImageUpload(recordId)

        if (response.code == "SUCCESS") {
            return response.result
        } else {
            throw NetworkErrorException(response.code)
        }
    }
}