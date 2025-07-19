package com.yapp.fitrun.core.domain.entity

data class HomeResultEntity(
    val userEntity: UserEntity,
    val recordEntity: RecordEntity,
    val userGoalEntity: UserGoalEntity,
)