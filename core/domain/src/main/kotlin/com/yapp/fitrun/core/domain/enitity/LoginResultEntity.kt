package com.yapp.fitrun.core.domain.enitity

data class LoginResultEntity(
    val tokenEntity: TokenEntity,
    val userEntity: UserEntity,
    val isNewUser: Boolean
)