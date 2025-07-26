package com.yapp.fitrun.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yapp.fitrun.core.room.model.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert
    suspend fun insertLocation(location: Location)

    @Insert
    suspend fun insertLocationList(locations: List<Location>)

    @Query("SELECT * FROM locations")
    fun getAllLocations(): Flow<List<Location>>

    @Query("DELETE FROM locations")
    suspend fun clearAll()
}
