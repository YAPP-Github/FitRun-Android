package com.yapp.fitrun.core.data.di

import com.yapp.fitrun.core.data.repository.AuthRepositoryImpl
import com.yapp.fitrun.core.data.repository.GoalRepositoryImpl
import com.yapp.fitrun.core.data.repository.HomeRepositoryImpl
import com.yapp.fitrun.core.data.repository.LocationRepositoryImpl
import com.yapp.fitrun.core.data.repository.RecordRepositoryImpl
import com.yapp.fitrun.core.domain.repository.TokenRepository
import com.yapp.fitrun.core.data.repository.TokenRepositoryImpl
import com.yapp.fitrun.core.data.repository.UserRepositoryImpl
import com.yapp.fitrun.core.data.repository.WorkThroughRepositoryImpl
import com.yapp.fitrun.core.domain.repository.AuthRepository
import com.yapp.fitrun.core.domain.repository.GoalRepository
import com.yapp.fitrun.core.domain.repository.HomeRepository
import com.yapp.fitrun.core.domain.repository.LocationRepository
import com.yapp.fitrun.core.domain.repository.RecordRepository
import com.yapp.fitrun.core.domain.repository.UserRepository
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

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindGoalRepository(goalRepositoryImpl: GoalRepositoryImpl): GoalRepository

    @Binds
    @Singleton
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository

    @Binds
    @Singleton
    abstract fun bindRecordRepository(recordRepository: RecordRepositoryImpl): RecordRepository
}
