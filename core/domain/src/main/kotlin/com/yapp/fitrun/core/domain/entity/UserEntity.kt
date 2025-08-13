package com.yapp.fitrun.core.domain.entity

data class UserEntity(
    val id: Long,
    val nickname: String,
    val email: String? = null,
    val provider: String? = null,
    val runnerType: String? = null,
)
