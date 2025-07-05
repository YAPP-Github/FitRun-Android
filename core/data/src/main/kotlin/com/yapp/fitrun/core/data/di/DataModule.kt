package com.yapp.fitrun.core.data.di

import com.yapp.fitrun.core.common.TokenProvider
import com.yapp.fitrun.core.data.local.TokenManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindTokenProvider(
        tokenManager: TokenManager
    ): TokenProvider

}