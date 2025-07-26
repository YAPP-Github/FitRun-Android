package com.yapp.fitrun.core.domain.repository

interface WorkThroughRepository {
    suspend fun setIsFirstTime(isFirstTime: Boolean = false)
    suspend fun getIsFirstTime(): Boolean
}
