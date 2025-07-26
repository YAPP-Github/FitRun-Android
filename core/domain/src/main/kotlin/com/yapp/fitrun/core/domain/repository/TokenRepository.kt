package com.yapp.fitrun.core.domain.repository

interface TokenRepository {
    suspend fun setAccessTokenSync(accessToken: String)
    suspend fun setRefreshTokenSync(refreshToken: String)

    suspend fun getAccessTokenSync(): String
    suspend fun getRefreshTokenSync(): String

    suspend fun clearTokens()
    suspend fun clear()
}
