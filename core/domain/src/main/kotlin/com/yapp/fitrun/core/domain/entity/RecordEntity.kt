package com.yapp.fitrun.core.domain.entity

data class RecordEntity(
    val recentDistanceMeter: Double? = null,
    val recentPace: Int? = null,
    val recentTime: Int? = null,
    val thisWeekRunningCount: Int,
    val totalDistance: Double,
)
