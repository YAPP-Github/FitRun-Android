package com.yapp.fitrun.core.network

import com.yapp.fitrun.core.network.model.request.OnBoardingRequest
import com.yapp.fitrun.core.network.model.response.OnBoardingResponse
import com.yapp.fitrun.core.network.model.response.RunnerResponse
import com.yapp.fitrun.core.network.model.response.UserResponse

interface UserDataSource {
    suspend fun getUserInfo(): UserResponse
    suspend fun deleteAccount()
    suspend fun getOnBoardingInfo(): OnBoardingResponse
    suspend fun setOnBoardingInfo(onBoardingRequest: OnBoardingRequest)
    suspend fun updateOnBoardingInfo(onBoardingRequest: OnBoardingRequest)
    suspend fun getUserRunnerType(): RunnerResponse
}