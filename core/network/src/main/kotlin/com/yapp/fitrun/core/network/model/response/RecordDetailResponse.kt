package com.yapp.fitrun.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecordDetailResponse(
    @SerialName("userId")
    val userId: Int,
    @SerialName("recordId")
    val recordId: Int,
    @SerialName("runningPoints")
    val runningPoints: List<RecordDetailRunningPoint>,
    @SerialName("totalTime")
    val totalTime: Long,
    @SerialName("totalDistance")
    val totalDistance: Double,
    @SerialName("startAt")
    val startAt: String,
    @SerialName("segments")
    val segments: List<RecordDetailSegments>,
)

@Serializable
data class RecordDetailRunningPoint(
    @SerialName("lon")
    val lon: Double,
    @SerialName("lat")
    val lat: Double,
)

@Serializable
data class RecordDetailSegments(
    @SerialName("orderNo")
    val orderNo: Int,
    @SerialName("distanceMeter")
    val distanceMeter: Double,
    @SerialName("averagePace")
    val averagePace: Long,
)
