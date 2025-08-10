package com.yapp.fitrun.core.domain.repository

import com.yapp.fitrun.core.domain.entity.running.RunningCompleteEntity
import com.yapp.fitrun.core.domain.entity.running.RunningStartEntity
import com.yapp.fitrun.core.domain.entity.running.RunningUploadImageEntity

interface RunningRepository {

    suspend fun setRunningStart(
        lat: Double,
        lon: Double,
        timeStamp: String,
    ): Result<RunningStartEntity>

    suspend fun setRunningComplete(
        recordId: Int,
        averagePace: Int,
        runningPoints: List<RunningPoint>,
        startAt: String,
        totalCalories: Int,
        totalDistance: Double,
        totalTime: Int,
    ): Result<RunningCompleteEntity>

    suspend fun setRunningUploadImage(
        recordId: Int,
    ): Result<RunningUploadImageEntity>
}

data class RunningPoint(
    val lat: Double,
    val lon: Double,
    val timeStamp: String,
    val totalRunningTimeMills: Int,
)
