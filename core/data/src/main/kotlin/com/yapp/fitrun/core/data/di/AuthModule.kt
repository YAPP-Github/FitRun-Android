package com.yapp.fitrun.core.data.di

import com.yapp.fitrun.core.data.AuthRepositoryImpl
import com.yapp.fitrun.core.data.local.TokenRepositoryImpl
import com.yapp.fitrun.core.domain.repository.AuthRepository
import com.yapp.fitrun.core.network.api.AuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApiService: AuthApiService,
        tokenRepositoryImpl: TokenRepositoryImpl,
        json: Json
    ): AuthRepository {
        return AuthRepositoryImpl(
            authApiService = authApiService,
            tokenRepositoryImpl = tokenRepositoryImpl,
            json = json
        )
    }
}