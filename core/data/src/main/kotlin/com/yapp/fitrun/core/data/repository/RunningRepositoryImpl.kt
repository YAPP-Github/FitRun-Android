package com.yapp.fitrun.core.data.repository

import com.yapp.fitrun.core.data.mapper.toEntity
import com.yapp.fitrun.core.data.mapper.toModel
import com.yapp.fitrun.core.domain.entity.running.RunningCompleteEntity
import com.yapp.fitrun.core.domain.entity.running.RunningStartEntity
import com.yapp.fitrun.core.domain.entity.running.RunningUploadImageEntity
import com.yapp.fitrun.core.domain.repository.RunningRepository
import com.yapp.fitrun.core.network.RunningDataSource
import com.yapp.fitrun.core.network.model.request.running.RunningCompleteRequest
import com.yapp.fitrun.core.network.model.request.running.RunningStartRequest
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class RunningRepositoryImpl @Inject constructor(
    private val runningDataSource: RunningDataSource
) : RunningRepository {
    override suspend fun setRunningStart(
        lat: Double,
        lon: Double,
        timeStamp: String
    ): Result<RunningStartEntity> {
        return runCatching {
            runningDataSource.setRunningStart(RunningStartRequest(lat, lon, timeStamp))
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

    override suspend fun setRunningComplete(
        recordId: Int,
        averagePace: Int,
        runningPoints: List<com.yapp.fitrun.core.domain.repository.RunningPoint>,
        startAt: String,
        totalCalories: Int,
        totalDistance: Double,
        totalTime: Int
    ): Result<RunningCompleteEntity> {
        return runCatching {
            runningDataSource.setRunningComplete(
                recordId,
                RunningCompleteRequest(
                    averagePace,
                    runningPoints.map { it.toModel() },
                    startAt,
                    totalCalories,
                    totalDistance,
                    totalTime,
                )
            )
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

    override suspend fun setRunningUploadImage(recordId: Int): Result<RunningUploadImageEntity> {
        return runCatching {
            runningDataSource.setRunningUploadImage(recordId)
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