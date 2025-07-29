package com.yapp.fitrun.feature.record.viewmodel

import com.naver.maps.geometry.LatLng

data class RecordDetailState(
    val isLoading: Boolean = false,
    val title: String = "",
    val recordId: Int = 0,
    val startAt: String = "",
    val totalTime: String = "",
    val averagePace: String = "",
    val totalDistance: Double = 0.0,
    val runningPoint: List<LatLng> = emptyList(),
    val segments: List<RecordDetailInfo> = emptyList(),
)

data class RecordDetailInfo(
    val orderNo: Int,
    val distanceMeter: Double,
    val averagePace: String,
)
