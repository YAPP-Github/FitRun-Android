package com.yapp.fitrun.core.data.repository

import com.yapp.fitrun.core.data.mapper.toEntity
import com.yapp.fitrun.core.domain.entity.HomeResultEntity
import com.yapp.fitrun.core.domain.repository.HomeRepository
import com.yapp.fitrun.core.network.HomeDataSource
import java.util.concurrent.CancellationException
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource,
) : HomeRepository {
    override suspend fun getHomeData(): Result<HomeResultEntity> {
        return runCatching {
            homeDataSource.getHomeInfo()
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
}
