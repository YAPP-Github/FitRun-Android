package com.yapp.fitrun.core.data.repository

import com.yapp.fitrun.core.data.mapper.toEntity
import com.yapp.fitrun.core.network.AuthDataSource
import com.yapp.fitrun.core.domain.enitity.LoginResultEntity
import com.yapp.fitrun.core.domain.enitity.TokenEntity
import com.yapp.fitrun.core.domain.repository.AuthRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {

    override suspend fun loginWithKakao(idToken: String): Result<LoginResultEntity> {
        return runCatching {
            authDataSource.loginWithKakao(idToken = idToken)
        }.fold(
            onSuccess = {
                Result.success(it.toEntity())
            },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            }
        )
    }

    override suspend fun logout(): Result<Unit> {
        return runCatching {
            authDataSource.logout()
        }.fold(
            onSuccess = {
                Result.success(Unit)
            },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            }
        )
    }

    override suspend fun updateRefreshToken(): Result<TokenEntity> {
        return runCatching {
            authDataSource.updateRefreshToken()
        }.fold(
            onSuccess = {
                Result.success(it.toEntity())
            },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                Result.failure(exception)
            }
        )
    }
}