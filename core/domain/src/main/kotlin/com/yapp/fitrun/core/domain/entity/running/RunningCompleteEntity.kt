package com.yapp.fitrun.core.domain.entity.running

data class RunningCompleteEntity(
    val recordId: Int,
    val runningPoints: List<RunningPoint>,
    val title: String,
    val userId: Int,
)

data class RunningPoint(
    val calories: Int,
    val distance: Double,
    val lat: Double,
    val lon: Double,
    val orderNo: Int,
    val pace: Int,
    val pointId: Int,
    val recordId: Int,
    val timeStamp: String,
    val totalRunningDistance: Double,
    val totalRunningTime: Int,
    val userId: Int,
)
