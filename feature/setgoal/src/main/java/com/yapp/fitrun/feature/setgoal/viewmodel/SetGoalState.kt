package com.yapp.fitrun.feature.setgoal.viewmodel

data class SetGoalState(
    val isLoading: Boolean = false,
    val isLoadingRecommendPace: Boolean = false,
    val goalEntity: com.yapp.fitrun.core.domain.entity.GoalEntity? = null,
    val paceGoal: Int? = null, // 초 단위
    val weeklyRunCount: Int? = null,
    val isRemindEnabled: Boolean = false,
    val recommendPace: Int? = null, // 초 단위
    val currentPaceInput: Int? = 420, // 7'00" = 420초
    val currentRunCountInput: Int? = 3,
    val currentRemindEnabled: Boolean = false,
)
