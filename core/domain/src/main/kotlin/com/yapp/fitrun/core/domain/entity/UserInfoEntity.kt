package com.yapp.fitrun.core.domain.entity

data class UserInfoEntity(
    val user: UserEntity,
    val goal: GoalEntity? = null,
)
