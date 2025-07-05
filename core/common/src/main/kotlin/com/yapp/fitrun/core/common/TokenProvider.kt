package com.yapp.fitrun.core.common

interface TokenProvider {
    suspend fun getAccessTokenSync(): String?
    suspend fun getRefreshTokenSync(): String?
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun clearTokens()
}