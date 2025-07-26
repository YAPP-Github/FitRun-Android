package com.yapp.fitrun.core.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yapp.fitrun.core.room.dao.LocationDao
import com.yapp.fitrun.core.room.model.Location

@Database(entities = [Location::class], version = 1)
abstract class RunningDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}
