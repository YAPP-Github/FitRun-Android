package com.yapp.fitrun.core.network.di

import com.yapp.fitrun.core.domain.repository.TokenRepository
import com.yapp.fitrun.core.network.AuthOkHttpClient
import com.yapp.fitrun.core.network.BaseOkHttpClient
import com.yapp.fitrun.core.network.BuildConfig
import com.yapp.fitrun.core.network.api.AuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        prettyPrint = BuildConfig.DEBUG
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenRepository: TokenRepository): Interceptor = Interceptor { chain ->
        val original = chain.request()

        // 로그인 API는 토큰 불필요
        if (original.url.encodedPath.contains("auth/login")) {
            return@Interceptor chain.proceed(original)
        }

        // 토큰 추가
        val token = runBlocking { tokenRepository.getAccessTokenSync() }

        val request = original.newBuilder().apply {
            token?.let {
                header("Authorization", "Bearer $it")
            }
        }.build()

        chain.proceed(request)
    }

    @Provides
    @Singleton
    @BaseOkHttpClient
    fun provideBaseOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @AuthOkHttpClient
    fun provideAuthOkHttpClient(
        authInterceptor: Interceptor,
        @BaseOkHttpClient baseClient: OkHttpClient
    ): OkHttpClient {
        return baseClient.newBuilder()
            .addInterceptor(authInterceptor)
            // TokenAuthenticator는 :core:data 모듈에서 추가
            .build()
    }

    @Provides
    @Singleton
    @Named("BaseRetrofit")
    fun provideBaseRetrofit(
        @BaseOkHttpClient okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl("http://fitrun.p-e.kr")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(
        @Named("BaseRetrofit") retrofit: Retrofit
    ): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }
}