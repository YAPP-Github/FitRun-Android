package com.yapp.fitrun.feature.mypage.viewmodel

import com.yapp.fitrun.core.designsystem.R

data class MyPageState(
    val isLoading: Boolean = false,

    val userNickName: String = "",
    val userEmail: String = "",
    val userLevel: String = "",
    val userRunningPurpose: String = "",
    val userLevelImageId: Int = R.drawable.img_chicken,
    val userRunningPurposeImageId: Int = R.drawable.img_fire,

    val userGoalDistance: String = "",
    val userGoalTime: String = "",
    val userGoalPace: String = "",
    val userGoalFrequency: String = "",

    // service
    val terms: String = "",
    val serviceUsage: String = "",

    // setting
    val isNotification: Boolean = false,
    val isAudioCoaching: Boolean = false,
    val isAudioFeedback: Boolean = false,
)
