package com.yapp.fitrun.feature.mypage.viewmodel

data class MyPageState(
    val isLoading: Boolean = false,

    val userNickName: String = "",
    val userEmail: String = "",
    val userLevel: String = "",
    val userRunningPurpose: String = "",

    val userGoalDistance: String = "",
    val userGoalTime: String = "",
    val userGoalPace: String = "",
    val userGoalFrequency: String = "",
)
