package com.yapp.fitrun.core.domain.repository

import com.yapp.fitrun.core.domain.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    suspend fun insertLocation(lat: Double, lng: Double)

    suspend fun insertLocationList(locations: List<LocationEntity>)

    fun getLocations(): Flow<List<LocationEntity>>

    suspend fun clearAll()
}
