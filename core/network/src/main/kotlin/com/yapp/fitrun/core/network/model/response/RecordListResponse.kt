package com.yapp.fitrun.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecordListResponse(
    @SerialName("userId")
    val userId: Int,
    @SerialName("records")
    val records: List<RecordData>,
    @SerialName("recordCount")
    val recordCount: Int,
    @SerialName("totalDistance")
    val totalDistance: Double,
    @SerialName("totalTime")
    val totalTime: Long,
    @SerialName("totalCalories")
    val totalCalories: Long,
    @SerialName("averagePace")
    val averagePace: Long,
    @SerialName("timeGoalAchievedCount")
    val timeGoalAchievedCount: Int,
    @SerialName("distanceGoalAchievedCount")
    val distanceGoalAchievedCount: Int,
)

@Serializable
data class RecordData(
    @SerialName("recordId")
    val recordId: Int,
    @SerialName("userId")
    val userId: Int,
    @SerialName("startAt")
    val startAt: String,
    @SerialName("averagePace")
    val averagePace: Long,
    @SerialName("totalDistance")
    val totalDistance: Double,
    @SerialName("totalTime")
    val totalTime: Long,
)
