package com.yapp.fitrun.core.network

import com.yapp.fitrun.core.network.model.response.HomeResponse

interface HomeDataSource {
    suspend fun getHomeInfo(): HomeResponse
}