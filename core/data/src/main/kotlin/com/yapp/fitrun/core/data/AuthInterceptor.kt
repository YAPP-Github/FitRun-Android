package com.yapp.fitrun.core.data

import com.yapp.fitrun.core.data.local.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        // 로그인 API는 토큰 불필요
        if (original.url.encodedPath.contains("auth/login")) {
            return chain.proceed(original)
        }

        // 토큰 추가
        val token = runBlocking { tokenManager.getAccessTokenSync() }

        val request = original.newBuilder().apply {
            token?.let {
                header("Authorization", "Bearer $it")
            }
        }.build()

        return chain.proceed(request)
    }
}