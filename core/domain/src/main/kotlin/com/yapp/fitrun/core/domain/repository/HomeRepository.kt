package com.yapp.fitrun.core.domain.repository

import com.yapp.fitrun.core.domain.entity.HomeResultEntity

interface HomeRepository {

    suspend fun getHomeData(): Result<HomeResultEntity>
}
