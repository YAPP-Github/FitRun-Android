package com.yapp.fitrun.core.network

import com.yapp.fitrun.core.network.api.HomeApiService
import com.yapp.fitrun.core.network.model.response.HomeResponse
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(
    private val service: HomeApiService
) : HomeDataSource {
    override suspend fun getHomeInfo(): HomeResponse {
        val response = service.getHomeInfo()

        if (response.code == "SUCCESS") {
            return response.result
        } else {
            throw CancellationException(response.code)
        }
    }
}