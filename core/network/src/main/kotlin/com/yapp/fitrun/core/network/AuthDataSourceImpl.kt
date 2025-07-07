package com.yapp.fitrun.core.network

import com.yapp.fitrun.core.network.api.AuthApiService
import com.yapp.fitrun.core.network.model.request.KakaoLoginRequest
import com.yapp.fitrun.core.network.model.response.LoginResponse
import com.yapp.fitrun.core.network.model.response.TokenResponse
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val service: AuthApiService,
): AuthDataSource {

    private val provider: String = "kakao"

    override suspend fun loginWithKakao(idToken: String): LoginResponse {
        return service.loginWithKakao(
            provider = provider,
            request = KakaoLoginRequest(
                idToken = idToken,
                nonce = null
            )
        )
    }

    override suspend fun logout() {
        service.logout()
    }

    override suspend fun updateRefreshToken(): TokenResponse {
        return service.updateRefreshToken()
    }
}