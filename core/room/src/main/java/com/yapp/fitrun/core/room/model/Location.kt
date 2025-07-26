package com.yapp.fitrun.core.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val lat: Double,
    val lng: Double,
)
