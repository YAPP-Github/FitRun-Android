package com.yapp.fitrun.core.data.di

import com.yapp.fitrun.core.data.repository.AuthRepositoryImpl
import com.yapp.fitrun.core.domain.repository.TokenRepository
import com.yapp.fitrun.core.data.repository.TokenRepositoryImpl
import com.yapp.fitrun.core.data.repository.WorkThroughRepositoryImpl
import com.yapp.fitrun.core.domain.repository.AuthRepository
import com.yapp.fitrun.core.domain.repository.WorkThroughRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTokenRepository(tokenRepositoryImpl: TokenRepositoryImpl): TokenRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindWorkThroughRepository(workThroughRepositoryImpl: WorkThroughRepositoryImpl): WorkThroughRepository
}