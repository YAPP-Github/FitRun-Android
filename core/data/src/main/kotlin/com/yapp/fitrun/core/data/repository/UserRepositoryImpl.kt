package com.yapp.fitrun.core.data.repository

import com.yapp.fitrun.core.data.mapper.toEntity
import com.yapp.fitrun.core.domain.entity.OnBoardingEntity
import com.yapp.fitrun.core.domain.entity.RunnerEntity
import com.yapp.fitrun.core.domain.entity.UserEntity
import com.yapp.fitrun.core.domain.repository.UserRepository
import com.yapp.fitrun.core.network.UserDataSource
import com.yapp.fitrun.core.network.model.request.OnBoardingAnswers
import com.yapp.fitrun.core.network.model.request.OnBoardingRequest
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {
    override suspend fun getUserInfo(): Result<UserEntity> {
        return runCatching {
            userDataSource.getUserInfo()
        }.fold(
            onSuccess = {
                Result.success(it.toEntity())
            },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun deleteAccount() {
        userDataSource.deleteAccount()
    }

    override suspend fun getOnBoardingInfo(): Result<OnBoardingEntity> {
        return runCatching {
            userDataSource.getOnBoardingInfo()
        }.fold(
            onSuccess = {
                Result.success(it.toEntity())
            },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun setOnBoardingInfo(onBoardingEntity: OnBoardingEntity) {
        userDataSource.setOnBoardingInfo(
            OnBoardingRequest(
                answers = onBoardingEntity.answers.map {
                    OnBoardingAnswers(
                        it.questionType,
                        it.answer,
                    )
                },
            ),
        )
    }

    override suspend fun updateOnBoardingInfo(onBoardingEntity: OnBoardingEntity) {
        userDataSource.updateOnBoardingInfo(
            OnBoardingRequest(
                answers = onBoardingEntity.answers.map {
                    OnBoardingAnswers(
                        it.questionType,
                        it.answer,
                    )
                },
            ),
        )
    }

    override suspend fun getUserRunnerType(): Result<RunnerEntity> {
        return runCatching {
            userDataSource.getUserRunnerType()
        }.fold(
            onSuccess = {
                Result.success(it.toEntity())
            },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }
}
