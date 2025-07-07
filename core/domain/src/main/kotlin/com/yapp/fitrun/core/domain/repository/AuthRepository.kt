package com.yapp.fitrun.core.domain.repository

import com.yapp.fitrun.core.domain.Entity.LoginResultEntity
import com.yapp.fitrun.core.domain.Entity.TokenEntity

interface AuthRepository {
    suspend fun loginWithKakao(idToken: String): Result<LoginResultEntity>
    suspend fun logout(): Result<Unit>
    suspend fun updateRefreshToken(): Result<TokenEntity>
}