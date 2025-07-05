package com.yapp.fitrun.core.network.api

import com.yapp.fitrun.core.network.model.BaseResponse
import com.yapp.fitrun.core.network.model.request.KakaoLoginRequest
import com.yapp.fitrun.core.network.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApiService {
    @POST("/api/v1/auth/login/{provider}")
    suspend fun loginWithKakao(
        @Path("provider") provider: String,
        @Body request: KakaoLoginRequest
    ): BaseResponse<LoginResponse>


    @POST("/api/v1/auth/logout")
    suspend fun logout()

    @POST("/api/v1/auth/refresh")
    suspend fun refreshToken(
        @Header("Refresh-Token") refreshToken: String
    ): BaseResponse<LoginResponse>
}