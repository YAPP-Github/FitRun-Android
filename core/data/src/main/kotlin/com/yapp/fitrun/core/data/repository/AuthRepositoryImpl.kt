package com.yapp.fitrun.core.data.repository

import com.yapp.fitrun.core.data.mapper.toEntity
import com.yapp.fitrun.core.network.AuthDataSource
import com.yapp.fitrun.core.domain.Entity.LoginResultEntity
import com.yapp.fitrun.core.domain.Entity.TokenEntity
import com.yapp.fitrun.core.domain.repository.AuthRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {

    override suspend fun loginWithKakao(idToken: String): Result<LoginResultEntity> {
        runCatching {
            authDataSource.loginWithKakao(idToken = idToken)
        }.also { response ->
            // Error handling
            val isException = response.exceptionOrNull()
            if (isException is CancellationException) throw isException

            return response.map { it.toEntity() }
        }
    }

    override suspend fun logout(): Result<Unit> {
        runCatching {
            authDataSource.logout()
        }.also { response ->
            // Error handling
            val isException = response.exceptionOrNull()
            if (isException is CancellationException) throw isException

            return Result.success(Unit)
        }
    }

    override suspend fun updateRefreshToken(): Result<TokenEntity> {
        runCatching {
            authDataSource.updateRefreshToken()
        }.also { response ->
            // Error handling
            val isException = response.exceptionOrNull()
            if (isException is CancellationException) throw isException

            return response.map { it.toEntity() }
        }
    }
}