package com.yapp.fitrun.feature.record.viewmodel

data class RecordState(
    val isLoading: Boolean = false,
    val totalDistance: Double = 0.0,
    val recordCount: Int = 0,
    val averagePace: String = "",
    val totalTime: String = "",
    val timeGoalAchievedCount: Int = 0,
    val distanceGoalAchievedCount: Int = 0,
    val recordList: MutableList<Record> = mutableListOf(),
)

data class Record(
    val recordId: Int = 0,
    val startAt: String = "",
    val totalTime: String = "",
    val averagePace: String = "",
    val totalDistance: Double = 0.0,
    val runningRouteImage: String = "",
)
