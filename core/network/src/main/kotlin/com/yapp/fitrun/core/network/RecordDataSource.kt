package com.yapp.fitrun.core.network

import com.yapp.fitrun.core.network.model.response.RecordDetailResponse
import com.yapp.fitrun.core.network.model.response.RecordListResponse

interface RecordDataSource {
    suspend fun getRecordList(): RecordListResponse
    suspend fun getRecordDetail(recordId: Int): RecordDetailResponse
    suspend fun deleteRecordDetail(recordId: Int)
}
