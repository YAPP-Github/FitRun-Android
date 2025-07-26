package com.yapp.fitrun.core.data.repository

import com.yapp.fitrun.core.data.mapper.toEntity
import com.yapp.fitrun.core.data.mapper.toModel
import com.yapp.fitrun.core.domain.entity.LocationEntity
import com.yapp.fitrun.core.domain.repository.LocationRepository
import com.yapp.fitrun.core.room.dao.LocationDao
import com.yapp.fitrun.core.room.model.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
) : LocationRepository {
    override suspend fun insertLocation(lat: Double, lng: Double) {
        locationDao.insertLocation(location = Location(lat = lat, lng = lng))
    }

    override suspend fun insertLocationList(locations: List<LocationEntity>) {
        locationDao.insertLocationList(locations = locations.map { it.toModel() })
    }

    override fun getLocations(): Flow<List<LocationEntity>> {
        return locationDao.getAllLocations().map { list -> list.map { it.toEntity() } }
    }

    override suspend fun clearAll() {
        locationDao.clearAll()
    }
}
