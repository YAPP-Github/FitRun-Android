package com.yapp.fitrun.core.network

import com.yapp.fitrun.core.datastore.TokenDataSource
import com.yapp.fitrun.core.datastore.di.TokenDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenDataSource: TokenDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        // 로그인 API는 토큰 불필요
        if (original.url.encodedPath.contains("auth/login")) {
            return chain.proceed(original)
        }

        // 토큰 추가
        val token = runBlocking { tokenDataSource.getRefreshToken() }

        val request = original.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}