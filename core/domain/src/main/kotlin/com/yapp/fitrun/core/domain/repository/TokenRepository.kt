package com.yapp.fitrun.core.domain.repository

interface TokenRepository {
    suspend fun getAccessTokenSync(): String?
    suspend fun getRefreshTokenSync(): String?
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun clearTokens()
}