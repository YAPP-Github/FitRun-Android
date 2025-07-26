package com.yapp.fitrun.core.network.di

import com.yapp.fitrun.core.datastore.TokenDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenDataSource: TokenDataSource,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        // 로그인 API는 토큰 불필요
        if (original.url.encodedPath.contains("auth/login")) {
            return chain.proceed(original)
        }

        // 토큰 추가
        val token = if (original.url.encodedPath.contains("auth/")) {
            runBlocking { tokenDataSource.getRefreshToken() }
        } else {
            runBlocking { tokenDataSource.getAccessToken() }
        }

        val request = original.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}
