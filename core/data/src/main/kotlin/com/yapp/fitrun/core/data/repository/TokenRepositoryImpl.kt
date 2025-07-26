package com.yapp.fitrun.core.data.repository

import com.yapp.fitrun.core.datastore.TokenDataSource
import com.yapp.fitrun.core.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenDataSource: TokenDataSource,
) : TokenRepository {
    override suspend fun setAccessTokenSync(accessToken: String) {
        tokenDataSource.setAccessToken(accessToken)
    }

    override suspend fun setRefreshTokenSync(refreshToken: String) {
        tokenDataSource.setRefreshToken(refreshToken)
    }

    override suspend fun getAccessTokenSync(): String {
        return tokenDataSource.getAccessToken()
    }

    override suspend fun getRefreshTokenSync(): String {
        return tokenDataSource.getRefreshToken()
    }

    override suspend fun clearTokens() {
        tokenDataSource.clearTokens()
    }

    override suspend fun clear() {
        tokenDataSource.clear()
    }
}
