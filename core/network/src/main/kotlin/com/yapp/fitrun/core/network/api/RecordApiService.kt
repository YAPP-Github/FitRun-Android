package com.yapp.fitrun.core.network.api

import com.yapp.fitrun.core.network.model.response.BaseResponse
import com.yapp.fitrun.core.network.model.response.RecordDetailResponse
import com.yapp.fitrun.core.network.model.response.RecordListResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface RecordApiService {
    @GET("/api/v1/records")
    suspend fun getRecordList(): BaseResponse<RecordListResponse>

    @GET("/api/v1/records/{recordId}")
    suspend fun getRecordDetail(
        @Path("recordId") recordId: Int,
    ): BaseResponse<RecordDetailResponse>

    @DELETE("/api/v1/records/{recordId}")
    suspend fun deleteRecordDetail(
        @Path("recordId") recordId: Int,
    ): BaseResponse<Unit>
}
