package com.yapp.fitrun.core.network.api

import com.yapp.fitrun.core.network.model.request.DeleteAccountRequest
import com.yapp.fitrun.core.network.model.request.OnBoardingRequest
import com.yapp.fitrun.core.network.model.request.RunnerRequest
import com.yapp.fitrun.core.network.model.response.BaseResponse
import com.yapp.fitrun.core.network.model.response.OnBoardingResponse
import com.yapp.fitrun.core.network.model.response.RunnerResponse
import com.yapp.fitrun.core.network.model.response.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApiService {
    @GET("/api/v1/users")
    suspend fun getUserInfo(): BaseResponse<UserInfoResponse>

    @HTTP(method = "DELETE", path = "/api/v1/users", hasBody = true)
    suspend fun deleteAccount(@Body deleteAccountRequest: DeleteAccountRequest)

    @GET("/api/v1/users/onboarding")
    suspend fun getOnBoardingInfo(): BaseResponse<OnBoardingResponse>

    @POST("/api/v1/users/onboarding")
    suspend fun setOnBoardingInfo(@Body onBoardingRequest: OnBoardingRequest)

    @PATCH("/api/v1/users/onboarding")
    suspend fun updateOnBoardingInfo(@Body onBoardingRequest: OnBoardingRequest)

    @GET("/api/v1/users/type")
    suspend fun getUserRunnerType(): BaseResponse<RunnerResponse>

    @PUT("/api/v1/users/type")
    suspend fun updateUserRunnerType(@Body runnerRequest: RunnerRequest): BaseResponse<RunnerResponse>
}
