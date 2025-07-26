package com.yapp.fitrun.core.data.repository

import com.yapp.fitrun.core.data.mapper.toEntity
import com.yapp.fitrun.core.domain.entity.RecordDetailEntity
import com.yapp.fitrun.core.domain.entity.RecordListEntity
import com.yapp.fitrun.core.domain.repository.RecordRepository
import com.yapp.fitrun.core.network.RecordDataSource
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class RecordRepositoryImpl @Inject constructor(
    private val recordDataSource: RecordDataSource,
) : RecordRepository {
    override suspend fun getRecordList(): Result<RecordListEntity> {
        return runCatching {
            recordDataSource.getRecordList()
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

    override suspend fun getRecordDetail(recordId: Int): Result<RecordDetailEntity> {
        return runCatching {
            recordDataSource.getRecordDetail(recordId)
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

    override suspend fun deleteRecordDetail(recordId: Int): Result<Unit> {
        return runCatching {
            recordDataSource.deleteRecordDetail(recordId)
        }
    }
}
