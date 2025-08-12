package com.yapp.fitrun.core.network

import com.yapp.fitrun.core.network.api.UserApiService
import com.yapp.fitrun.core.network.model.request.DeleteAccountRequest
import com.yapp.fitrun.core.network.model.request.OnBoardingRequest
import com.yapp.fitrun.core.network.model.request.RunnerRequest
import com.yapp.fitrun.core.network.model.response.OnBoardingResponse
import com.yapp.fitrun.core.network.model.response.RunnerResponse
import com.yapp.fitrun.core.network.model.response.UserInfoResponse
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class UserDataSourceImpl @Inject constructor(
    private val service: UserApiService,
) : UserDataSource {

    override suspend fun getUserInfo(): UserInfoResponse {
        val response = service.getUserInfo()

        if (response.code == "SUCCESS") {
            return response.result
        } else {
            throw CancellationException(response.code)
        }
    }

    override suspend fun deleteAccount(deleteAccountRequest: DeleteAccountRequest) {
        service.deleteAccount(deleteAccountRequest)
    }

    override suspend fun getOnBoardingInfo(): OnBoardingResponse {
        val response = service.getOnBoardingInfo()

        if (response.code == "SUCCESS") {
            return response.result
        } else {
            throw CancellationException(response.code)
        }
    }

    override suspend fun setOnBoardingInfo(onBoardingRequest: OnBoardingRequest) {
        service.setOnBoardingInfo(onBoardingRequest)
    }

    override suspend fun updateOnBoardingInfo(onBoardingRequest: OnBoardingRequest) {
        service.updateOnBoardingInfo(onBoardingRequest)
    }

    override suspend fun getUserRunnerType(): RunnerResponse {
        val response = service.getUserRunnerType()

        if (response.code == "SUCCESS") {
            return response.result
        } else {
            throw CancellationException(response.code)
        }
    }

    override suspend fun updateUserRunnerType(runnerRequest: RunnerRequest): RunnerResponse {
        val response = service.updateUserRunnerType(runnerRequest)

        if (response.code == "SUCCESS") {
            return response.result
        } else {
            throw CancellationException(response.code)
        }
    }
}
