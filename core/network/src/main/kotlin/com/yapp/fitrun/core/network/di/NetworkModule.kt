package com.yapp.fitrun.core.network.di

import com.yapp.fitrun.core.datastore.TokenDataSource
import com.yapp.fitrun.core.network.AuthDataSource
import com.yapp.fitrun.core.network.AuthDataSourceImpl
import com.yapp.fitrun.core.network.AuthInterceptor
import com.yapp.fitrun.core.network.AuthOkHttpClient
import com.yapp.fitrun.core.network.BaseOkHttpClient
import com.yapp.fitrun.core.network.BuildConfig
import com.yapp.fitrun.core.network.api.AuthApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

private val jsonRule = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    prettyPrint = true
    isLenient = true
}

private val jsonConverterFactory = jsonRule.asConverterFactory("application/json".toMediaType())

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindAuthDataSource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource
}

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    internal fun provideAuthInterceptor(tokenDataSource: TokenDataSource): AuthInterceptor {
        return AuthInterceptor(tokenDataSource)
    }

    @Provides
    @Singleton
    @BaseOkHttpClient
    internal fun provideBaseOkHttpClient(): OkHttpClient {
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
    internal fun provideAuthOkHttpClient(
        authInterceptor: Interceptor,
        @BaseOkHttpClient baseClient: OkHttpClient
    ): OkHttpClient {
        return baseClient.newBuilder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("BaseRetrofit")
    internal fun provideBaseRetrofit(
        @BaseOkHttpClient okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://fitrun.p-e.kr")
            .client(okHttpClient)
            .addConverterFactory(jsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideAuthApiService(
        @Named("BaseRetrofit") retrofit: Retrofit
    ): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }
}
