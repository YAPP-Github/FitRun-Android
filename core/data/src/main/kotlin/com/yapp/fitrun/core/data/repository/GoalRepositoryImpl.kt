package com.yapp.fitrun.core.data.repository

import com.yapp.fitrun.core.domain.repository.GoalRepository
import com.yapp.fitrun.core.network.GoalDataSource
import com.yapp.fitrun.core.network.model.request.RunningPurposeRequest
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GoalRepositoryImpl @Inject constructor(
    private val goalDatasource: GoalDataSource
): GoalRepository {
    override suspend fun setRunningPurpose(purpose: String): Result<Unit> {
        return runCatching {
            goalDatasource.setRunningPurpose(RunningPurposeRequest(purpose))
        }.fold(
            onSuccess = {
                Result.success(Unit)
            },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            }
        )
    }

    override suspend fun updateRunningPurpose(purpose: String) {
        goalDatasource.updateRunningPurpose(RunningPurposeRequest(purpose))
    }
}