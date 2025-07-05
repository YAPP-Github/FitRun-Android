package com.yapp.fitrun.core.data

import com.yapp.fitrun.core.data.local.TokenManager
import com.yapp.fitrun.core.network.api.AuthApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val authApiService: AuthApiService,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // 401 응답을 받았을 때
        if (response.code != 401) return null

        // 이미 재시도한 경우 null 반환
        if (response.request.header("Authorization-Retry") != null) {
            return null
        }

        val refreshToken = runBlocking { tokenManager.refreshToken.first() }
            ?: return null

        return runBlocking {
            try {
                // 토큰 갱신 API 호출
                val apiResponse = authApiService.refreshToken(refreshToken)

                if (apiResponse.code != "SUCCESS") {
                    tokenManager.clearTokens()
                    return@runBlocking null
                }

                // 새 토큰 저장
                tokenManager.saveTokens(
                    accessToken = apiResponse.result.tokenResponse.accessToken,
                    refreshToken = apiResponse.result.tokenResponse.refreshToken
                )

                // 새 토큰으로 요청 재시도
                response.request.newBuilder()
                    .removeHeader("Authorization")
                    .header("Authorization", "Bearer ${apiResponse.result.tokenResponse.accessToken}")
                    .header("Authorization-Retry", "true")
                    .build()
            } catch (e: Exception) {
                // 토큰 갱신 실패 시 로그인 화면으로 이동하도록 처리
                tokenManager.clearTokens()
                null
            }
        }
    }
}