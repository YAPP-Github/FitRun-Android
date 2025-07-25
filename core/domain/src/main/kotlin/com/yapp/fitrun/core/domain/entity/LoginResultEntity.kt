package com.yapp.fitrun.core.domain.entity

data class LoginResultEntity(
    val tokenEntity: TokenEntity,
    val userEntity: UserEntity,
    val isNewUser: Boolean,
)
