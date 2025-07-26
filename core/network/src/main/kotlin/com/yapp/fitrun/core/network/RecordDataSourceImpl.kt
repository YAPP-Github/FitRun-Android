package com.yapp.fitrun.core.network

import android.accounts.NetworkErrorException
import com.yapp.fitrun.core.network.api.RecordApiService
import com.yapp.fitrun.core.network.model.response.RecordDetailResponse
import com.yapp.fitrun.core.network.model.response.RecordListResponse
import javax.inject.Inject

class RecordDataSourceImpl @Inject constructor(
    private val service: RecordApiService,
) : RecordDataSource {
    override suspend fun getRecordList(): RecordListResponse {
        val response = service.getRecordList()

        if (response.code == "SUCCESS") {
            return response.result
        } else {
            throw NetworkErrorException(response.code)
        }
    }

    override suspend fun getRecordDetail(recordId: Int): RecordDetailResponse {
        val response = service.getRecordDetail(recordId)

        if (response.code == "SUCCESS") {
            return response.result
        } else {
            throw NetworkErrorException(response.code)
        }
    }

    override suspend fun deleteRecordDetail(recordId: Int) {
        val response = service.deleteRecordDetail(recordId)
        if (response.code != "SUCCESS") {
            throw NetworkErrorException(response.code)
        }
    }
}
