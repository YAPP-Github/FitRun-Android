package com.yapp.fitrun.feature.home.viewmodel

import android.location.Location
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.domain.entity.HomeResultEntity

data class HomeState(
    val isLoading: Boolean = false,
    val isLocationLoading: Boolean = false,
    val homeResult: HomeResultEntity? = null,
    val currentLocation: Location? = null,
    val error: String? = null,
    val locationError: String? = null,
) {
    private val distance = homeResult?.recordEntity?.totalDistance
    val homeTitleResId: Int?
        get() = when (distance) {
            null -> R.string.total_distance_message_0km
            0.0 -> R.string.total_distance_message_0km
            in 0.0..0.5 -> R.string.total_distance_message_0_5km
            in 0.5..1.0 -> R.string.total_distance_message_1km
            in 1.0..2.0 -> R.string.total_distance_message_2km
            in 2.0..3.0 -> R.string.total_distance_message_3km
            in 3.0..5.0 -> R.string.total_distance_message_5km
            in 5.0..7.0 -> R.string.total_distance_message_7km
            in 7.0..10.0 -> R.string.total_distance_message_10km
            in 10.0..12.0 -> R.string.total_distance_message_12km
            in 12.0..15.0 -> R.string.total_distance_message_15km
            in 15.0..18.0 -> R.string.total_distance_message_18km
            in 18.0..20.0 -> R.string.total_distance_message_20km
            in 20.0..25.0 -> R.string.total_distance_message_25km
            in 25.0..30.0 -> R.string.total_distance_message_30km
            in 30.0..35.0 -> R.string.total_distance_message_35km
            in 35.0..40.0 -> R.string.total_distance_message_40km
            in 40.0..45.0 -> R.string.total_distance_message_45km
            in 45.0..50.0 -> R.string.total_distance_message_50km
            in 50.0..55.0 -> R.string.total_distance_message_55km
            in 55.0..60.0 -> R.string.total_distance_message_60km
            in 60.0..70.0 -> R.string.total_distance_message_70km
            in 70.0..80.0 -> R.string.total_distance_message_80km
            in 80.0..90.0 -> R.string.total_distance_message_90km
            in 90.0..100.0 -> R.string.total_distance_message_100km
            in 100.0..120.0 -> R.string.total_distance_message_120km
            in 120.0..150.0 -> R.string.total_distance_message_150km
            in 150.0..180.0 -> R.string.total_distance_message_180km
            in 180.0..200.0 -> R.string.total_distance_message_200km
            in 200.0..250.0 -> R.string.total_distance_message_250km
            in 250.0..300.0 -> R.string.total_distance_message_300km
            else -> R.string.total_distance_message_300km // 300km 이상은 같은 메시지 사용
        }
}
