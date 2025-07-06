package com.yapp.fitrun.core.domain.repository

import com.yapp.fitrun.core.domain.model.LoginResult

interface AuthRepository {
    suspend fun loginWithKakao(
        idToken: String,
        nickname: String
    ): Result<LoginResult>

    suspend fun logout(): Result<Unit>

    suspend fun refreshToken(
        refreshToken: String,
    ): Result<LoginResult>
}