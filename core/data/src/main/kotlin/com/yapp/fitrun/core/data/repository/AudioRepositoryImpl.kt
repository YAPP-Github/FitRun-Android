package com.yapp.fitrun.core.data.repository

import com.yapp.fitrun.core.domain.entity.AudioEntity
import com.yapp.fitrun.core.domain.repository.AudioRepository
import com.yapp.fitrun.core.network.AudioDataSource
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class AudioRepositoryImpl @Inject constructor(
    private val audioDataSource: AudioDataSource,
) : AudioRepository {

    override suspend fun getCoach(): Result<AudioEntity> {
        return runCatching {
            val audioData = audioDataSource.getAudioCoach()
            AudioEntity(audioData = audioData)
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun getRunningInfo(paceMills: String): Result<AudioEntity> {
        return runCatching {
            val audioData = audioDataSource.getRunningInfo(paceMills)
            AudioEntity(audioData = audioData)
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun getDistanceFeedback(type: String): Result<AudioEntity> {
        return runCatching {
            val audioData = audioDataSource.getDistanceFeedback(type)
            AudioEntity(audioData = audioData)
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun getPaceFeedback(type: String): Result<AudioEntity> {
        return runCatching {
            val audioData = audioDataSource.getPaceFeedback(type)
            AudioEntity(audioData = audioData)
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }

    override suspend fun getTimeFeedback(type: String): Result<AudioEntity> {
        return runCatching {
            val audioData = audioDataSource.getTimeFeedback(type)
            AudioEntity(audioData = audioData)
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            },
        )
    }
}
