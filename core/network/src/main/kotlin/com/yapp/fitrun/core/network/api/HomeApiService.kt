package com.yapp.fitrun.core.network.api

import com.yapp.fitrun.core.network.model.response.BaseResponse
import com.yapp.fitrun.core.network.model.response.HomeResponse
import retrofit2.http.GET

interface HomeApiService {

    @GET("/api/v1/home")
    suspend fun getHomeInfo(): BaseResponse<HomeResponse>

}