package com.yapp.fitrun.core.network.model.request.running

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunningCompleteRequest(
    @SerialName("averagePace")
    val averagePace: Int,
    @SerialName("runningPoints")
    val runningPoints: List<RunningPoint>,
    @SerialName("startAt")
    val startAt: String,
    @SerialName("totalCalories")
    val totalCalories: Int,
    @SerialName("totalDistance")
    val totalDistance: Double,
    @SerialName("totalTime")
    val totalTime: Int
)

@Serializable
data class RunningPoint(
    val lat: Double,
    val lon: Double,
    val timeStamp: String,
    val totalRunningTimeMills: Int
)

