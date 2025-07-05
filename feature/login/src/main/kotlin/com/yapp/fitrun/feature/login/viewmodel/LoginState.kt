package com.yapp.fitrun.feature.login.viewmodel

import com.yapp.fitrun.core.domain.model.User


// State
data class LoginState(
    val isLoading: Boolean = false,
    val user: User? = null
)
