package com.yapp.fitrun.core.data.repository

import com.yapp.fitrun.core.data.mapper.toEntity
import com.yapp.fitrun.core.domain.entity.GoalEntity
import com.yapp.fitrun.core.domain.repository.GoalRepository
import com.yapp.fitrun.core.network.GoalDataSource
import com.yapp.fitrun.core.network.model.request.goal.DistanceGoalRequest
import com.yapp.fitrun.core.network.model.request.goal.PaceGoalRequest
import com.yapp.fitrun.core.network.model.request.goal.RunningPurposeRequest
import com.yapp.fitrun.core.network.model.request.goal.TimeGoalRequest
import com.yapp.fitrun.core.network.model.request.goal.WeeklyRunCountRequest
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GoalRepositoryImpl @Inject constructor(
    private val goalDatasource: GoalDataSource,
) : GoalRepository {

    override suspend fun getGoal(): Result<GoalEntity> {
        return runCatching {
            val response = goalDatasource.getGoal()
            response.result.toEntity()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun setDistanceGoal(distance: Double): Result<GoalEntity> {
        return runCatching {
            val response = goalDatasource.setDistanceGoal(DistanceGoalRequest(distance))
            response.result.toEntity()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun updateDistanceGoal(distance: Double): Result<GoalEntity> {
        return runCatching {
            val response = goalDatasource.updateDistanceGoal(DistanceGoalRequest(distance))
            response.result.toEntity()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun setPaceGoal(pace: Int): Result<GoalEntity> {
        return runCatching {
            val response = goalDatasource.setPaceGoal(PaceGoalRequest(pace))
            response.result.toEntity()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun updatePaceGoal(pace: Int): Result<GoalEntity> {
        return runCatching {
            val response = goalDatasource.updatePaceGoal(PaceGoalRequest(pace))
            response.result.toEntity()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun setTimeGoal(time: Int): Result<GoalEntity> {
        return runCatching {
            val response = goalDatasource.setTimeGoal(TimeGoalRequest(time))
            response.result.toEntity()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun updateTimeGoal(time: Int): Result<GoalEntity> {
        return runCatching {
            val response = goalDatasource.updateTimeGoal(TimeGoalRequest(time))
            response.result.toEntity()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun setWeeklyRunningCountGoal(
        count: Int,
        isRemindEnabled: Boolean,
    ): Result<GoalEntity> {
        return runCatching {
            val response = goalDatasource.setWeeklyRunningCountGoal(
                WeeklyRunCountRequest(count, isRemindEnabled),
            )
            response.result.toEntity()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun updateWeeklyRunningCountGoal(
        count: Int,
        isRemindEnabled: Boolean,
    ): Result<GoalEntity> {
        return runCatching {
            val response = goalDatasource.updateWeeklyRunCountGoal(
                WeeklyRunCountRequest(count, isRemindEnabled),
            )
            response.result.toEntity()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun setRunningPurpose(purpose: String): Result<Unit> {
        return runCatching {
            goalDatasource.setRunningPurpose(RunningPurposeRequest(purpose))
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun updateRunningPurpose(purpose: String): Result<Unit> {
        return runCatching {
            goalDatasource.updateRunningPurpose(RunningPurposeRequest(purpose))
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun getRecommendPace(): Result<Int> {
        return runCatching {
            val response = goalDatasource.getRecommendPace()
            response.result.recommendPace
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }
}
