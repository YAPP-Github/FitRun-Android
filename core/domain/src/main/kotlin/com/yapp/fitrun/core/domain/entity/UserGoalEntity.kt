package com.yapp.fitrun.core.domain.entity

data class UserGoalEntity(
    val distanceMeterGoal: Double? = null,
    val paceGoal: Int,
    val runningPurpose: String,
    val timeGoal: Int? = null,
    val weeklyRunningCount: Int? = null
)