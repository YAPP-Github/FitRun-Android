package com.yapp.fitrun.core.domain.model

data class LoginResult(
    val user: User,
    val isNewUser: Boolean
)