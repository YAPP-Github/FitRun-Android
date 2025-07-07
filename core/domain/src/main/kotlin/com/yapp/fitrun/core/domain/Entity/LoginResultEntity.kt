package com.yapp.fitrun.core.domain.Entity

data class LoginResultEntity(
    val tokenEntity: TokenEntity,
    val userEntity: UserEntity,
    val isNewUser: Boolean
)