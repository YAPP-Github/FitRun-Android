package com.yapp.fitrun.core.network.di

import com.yapp.fitrun.core.datastore.TokenDataSource
import com.yapp.fitrun.core.network.AuthDataSource
import com.yapp.fitrun.core.network.AuthDataSourceImpl
import com.yapp.fitrun.core.network.BuildConfig
import com.yapp.fitrun.core.network.GoalDataSource
import com.yapp.fitrun.core.network.GoalDataSourceImpl
import com.yapp.fitrun.core.network.HomeDataSource
import com.yapp.fitrun.core.network.HomeDataSourceImpl
import com.yapp.fitrun.core.network.RecordDataSource
import com.yapp.fitrun.core.network.RecordDataSourceImpl
import com.yapp.fitrun.core.network.UserDataSource
import com.yapp.fitrun.core.network.UserDataSourceImpl
import com.yapp.fitrun.core.network.api.AuthApiService
import com.yapp.fitrun.core.network.api.GoalApiService
import com.yapp.fitrun.core.network.api.HomeApiService
import com.yapp.fitrun.core.network.api.RecordApiService
import com.yapp.fitrun.core.network.api.UserApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
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

    @Singleton
    @Binds
    abstract fun bindUserDataSource(userDataSourceImpl: UserDataSourceImpl): UserDataSource

    @Singleton
    @Binds
    abstract fun bindGoalDataSource(goalDataSourceImpl: GoalDataSourceImpl): GoalDataSource

    @Singleton
    @Binds
    abstract fun bindHomeDataSource(homeDataSourceImpl: HomeDataSourceImpl): HomeDataSource

    @Singleton
    @Binds
    abstract fun bindRecordDataSource(recordDataSourceImpl: RecordDataSourceImpl): RecordDataSource
}

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    internal fun provideTokenInterceptor(tokenDataSource: TokenDataSource): TokenInterceptor {
        return TokenInterceptor(tokenDataSource)
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
    @TokenOkHttpClient
    internal fun provideTokenOkHttpClient(
        tokenInterceptor: TokenInterceptor,
        @BaseOkHttpClient baseClient: OkHttpClient,
    ): OkHttpClient {
        return baseClient.newBuilder()
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("BaseRetrofit")
    internal fun provideBaseRetrofit(
        @TokenOkHttpClient okHttpClient: OkHttpClient,
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
        @Named("BaseRetrofit") retrofit: Retrofit,
    ): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideUserApiService(
        @Named("BaseRetrofit") retrofit: Retrofit,
    ): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideGoalApiService(
        @Named("BaseRetrofit") retrofit: Retrofit,
    ): GoalApiService {
        return retrofit.create(GoalApiService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideHomeApiService(
        @Named("BaseRetrofit") retrofit: Retrofit,
    ): HomeApiService {
        return retrofit.create(HomeApiService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideRecordApiService(
        @Named("BaseRetrofit") retrofit: Retrofit,
    ): RecordApiService {
        return retrofit.create(RecordApiService::class.java)
    }
}
