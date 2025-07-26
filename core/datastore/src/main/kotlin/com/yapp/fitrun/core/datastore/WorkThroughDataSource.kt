package com.yapp.fitrun.core.datastore

interface WorkThroughDataSource {
    suspend fun setIsFirstTime(isFirstTime: Boolean = false)
    suspend fun getIsFirstTime(): Boolean
}
