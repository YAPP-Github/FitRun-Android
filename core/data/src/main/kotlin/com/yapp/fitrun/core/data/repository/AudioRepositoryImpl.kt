package com.yapp.fitrun.core.data.repository

import com.yapp.fitrun.core.data.mapper.toEntity
import com.yapp.fitrun.core.domain.entity.AudioEntity
import com.yapp.fitrun.core.domain.repository.AudioRepository
import com.yapp.fitrun.core.network.AudioDataSource
import com.yapp.fitrun.core.network.model.request.audio.DistanceFeedbackRequest
import com.yapp.fitrun.core.network.model.request.audio.PaceFeedbackRequest
import com.yapp.fitrun.core.network.model.request.audio.RunningInfoRequest
import com.yapp.fitrun.core.network.model.request.audio.TimeFeedbackRequest
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class AudioRepositoryImpl @Inject constructor(
    private val audioDataSource: AudioDataSource,
) : AudioRepository {

    override suspend fun getCoach(): Result<AudioEntity> {
        return runCatching {
            audioDataSource.getAudioCoach()
        }.fold(
            onSuccess = { Result.success(it.toEntity()) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun getRunningInfo(paceMills: String): Result<AudioEntity> {
        return runCatching {
            audioDataSource.getRunningInfo(RunningInfoRequest(paceMills))
        }.fold(
            onSuccess = { Result.success(it.toEntity()) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun getDistanceFeedback(type: String): Result<AudioEntity> {
        return runCatching {
            audioDataSource.getDistanceFeedback(DistanceFeedbackRequest(type))
        }.fold(
            onSuccess = { Result.success(it.toEntity()) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun getPaceFeedback(type: String): Result<AudioEntity> {
        return runCatching {
            audioDataSource.getPaceFeedback(PaceFeedbackRequest(type))
        }.fold(
            onSuccess = { Result.success(it.toEntity()) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun getTimeFeedback(type: String): Result<AudioEntity> {
        return runCatching {
            audioDataSource.getTimeFeedback(TimeFeedbackRequest(type))
        }.fold(
            onSuccess = { Result.success(it.toEntity()) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }
}
