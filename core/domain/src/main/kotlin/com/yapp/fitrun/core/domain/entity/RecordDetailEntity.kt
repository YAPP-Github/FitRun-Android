package com.yapp.fitrun.core.domain.entity

data class RecordDetailEntity(
    val userId: Int,
    val title: String,
    val recordId: Int,
    val runningPoints: List<RecordDetailRunningPointEntity>,
    val totalTime: Long,
    val totalDistance: Double,
    val averagePace: Long,
    val startAt: String,
    val segments: List<RecordDetailSegmentsEntity>,
)

data class RecordDetailRunningPointEntity(
    val lon: Double,
    val lat: Double,
)

data class RecordDetailSegmentsEntity(
    val orderNo: Int,
    val distanceMeter: Double,
    val averagePace: Long,
)
