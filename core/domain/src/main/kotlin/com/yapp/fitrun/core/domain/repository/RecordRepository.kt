package com.yapp.fitrun.core.domain.repository

import com.yapp.fitrun.core.domain.entity.RecordDetailEntity
import com.yapp.fitrun.core.domain.entity.RecordListEntity

interface RecordRepository {
    suspend fun getRecordList(): Result<RecordListEntity>
    suspend fun getRecordDetail(recordId: Int): Result<RecordDetailEntity>
    suspend fun deleteRecordDetail(recordId: Int)
}
