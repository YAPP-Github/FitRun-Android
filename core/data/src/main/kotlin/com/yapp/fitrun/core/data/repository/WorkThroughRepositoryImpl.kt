package com.yapp.fitrun.core.data.repository

import com.yapp.fitrun.core.datastore.WorkThroughDataSource
import com.yapp.fitrun.core.domain.repository.WorkThroughRepository
import javax.inject.Inject

class WorkThroughRepositoryImpl @Inject constructor(
    private val workThroughDataSource: WorkThroughDataSource,
) : WorkThroughRepository {

    override suspend fun setIsFirstTime(isFirstTime: Boolean) {
        workThroughDataSource.setIsFirstTime(isFirstTime)
    }

    override suspend fun getIsFirstTime(): Boolean {
        return workThroughDataSource.getIsFirstTime()
    }
}
