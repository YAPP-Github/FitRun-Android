package com.yapp.fitrun.core.domain.entity

data class RecordListEntity(
    val userId: Int,
    val records: List<RecordDataEntity>,
    val recordCount: Int,
    val totalDistance: Double,
    val totalTime: Long,
    val totalCalories: Long,
    val averagePace: Long,
    val timeGoalAchievedCount: Int,
    val distanceGoalAchievedCount: Int,
)

data class RecordDataEntity(
    val recordId: Int,
    val userId: Int,
    val startAt: String,
    val averagePace: Long,
    val totalDistance: Double,
    val totalTime: Long,
    val imageUrl: String,
)
