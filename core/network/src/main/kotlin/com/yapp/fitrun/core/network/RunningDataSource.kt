package com.yapp.fitrun.core.network

import com.yapp.fitrun.core.network.model.request.running.RunningCompleteRequest
import com.yapp.fitrun.core.network.model.request.running.RunningStartRequest
import com.yapp.fitrun.core.network.model.response.running.RunningCompleteResponse
import com.yapp.fitrun.core.network.model.response.running.RunningStartResponse
import com.yapp.fitrun.core.network.model.response.running.RunningUploadImageResponse

interface RunningDataSource {
    suspend fun setRunningStart(runningStartRequest: RunningStartRequest): RunningStartResponse
    suspend fun setRunningComplete(recordId: Int, runningCompleteRequest: RunningCompleteRequest): RunningCompleteResponse
    suspend fun setRunningUploadImage(recordId: Int): RunningUploadImageResponse
}
