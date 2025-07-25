package com.yapp.fitrun.core.datastore

interface TokenDataSource {
    suspend fun setAccessToken(accessToken: String)
    suspend fun setRefreshToken(refreshToken: String)

    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String

    suspend fun clear()
    suspend fun clearTokens()
}
