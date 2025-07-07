package com.yapp.fitrun.core.network

import com.yapp.fitrun.core.network.api.AuthApiService
import com.yapp.fitrun.core.network.model.request.KakaoLoginRequest
import com.yapp.fitrun.core.network.model.response.LoginResponse
import com.yapp.fitrun.core.network.model.response.TokenResponse
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val service: AuthApiService,
): AuthDataSource {

    private val provider: String = "kakao"

    override suspend fun loginWithKakao(idToken: String): LoginResponse {
        val response = service.loginWithKakao(
            provider = provider,
            request = KakaoLoginRequest(
                idToken = idToken,
                nonce = null
            )
        )

        if (response.code.equals("SUCCESS")) {
            return response.result
        }
        else {
            throw CancellationException(response.code)
        }
    }

    override suspend fun logout() {
        service.logout()
    }

    override suspend fun updateRefreshToken(): TokenResponse {
        val response = service.updateRefreshToken()

        if (response.code.equals("SUCCESS")) {
            return response.result
        }
        else {
            throw CancellationException(response.code)
        }
    }
}