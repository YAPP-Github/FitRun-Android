package com.yapp.fitrun.core.domain.repository

import com.yapp.fitrun.core.domain.entity.OnBoardingEntity
import com.yapp.fitrun.core.domain.entity.RunnerEntity
import com.yapp.fitrun.core.domain.entity.UserEntity

interface UserRepository {
    suspend fun getUserInfo(): Result<UserEntity>
    suspend fun deleteAccount()
    suspend fun getOnBoardingInfo(): Result<OnBoardingEntity>
    suspend fun setOnBoardingInfo(onBoardingEntity: OnBoardingEntity)
    suspend fun updateOnBoardingInfo(onBoardingEntity: OnBoardingEntity)
    suspend fun getUserRunnerType(): Result<RunnerEntity>
}