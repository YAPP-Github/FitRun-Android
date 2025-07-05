package com.yapp.fitrun.core.data

import com.yapp.fitrun.core.data.local.TokenManager
import com.yapp.fitrun.core.data.mapper.toDomainModel
import com.yapp.fitrun.core.domain.model.LoginResult
import com.yapp.fitrun.core.domain.repository.AuthRepository
import com.yapp.fitrun.core.network.api.AuthApiService
import com.yapp.fitrun.core.network.model.ErrorResponse
import com.yapp.fitrun.core.network.model.request.KakaoLoginRequest
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val tokenManager: TokenManager,
    private val json: Json
) : AuthRepository {
    override suspend fun loginWithKakao(
        idToken: String,
        nickname: String
    ): Result<LoginResult> {
        return try {
            val response = authApiService.loginWithKakao(
                provider = "KAKAO",
                request = KakaoLoginRequest(
                    idToken = idToken,
                    name = nickname,
                    nonce = null
                )
            )

            // SUCCESS 체크
            if (response.code != "SUCCESS") {
                return Result.failure(Exception("Login failed: ${response.code}"))
            }

            // 토큰 및 사용자 정보 저장
            val loginResponse = response.result
            tokenManager.saveLoginInfo(
                accessToken = loginResponse.tokenResponse.accessToken,
                refreshToken = loginResponse.tokenResponse.refreshToken,
            )
            Result.success(loginResponse.toDomainModel())
        } catch (e: HttpException) {
            // HTTP 에러 처리
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = errorBody?.let {
                try {
                    json.decodeFromString<ErrorResponse>(it)
                } catch (parseError: Exception) {
                    null
                }
            }

            val errorMessage = errorResponse?.message ?: e.message()
            Result.failure(Exception(errorMessage))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            authApiService.logout()
            tokenManager.clearAll() // 모든 저장 데이터 삭제
            Result.success(Unit)
        } catch (e: Exception) {
            // 로그아웃은 실패해도 로컬 데이터는 삭제
            tokenManager.clearAll()
            Result.failure(e)
        }
    }

    override suspend fun refreshToken(refreshToken: String): Result<LoginResult> {
        return try {
            val response = authApiService.refreshToken(refreshToken)

            if (response.code != "SUCCESS") {
                return Result.failure(Exception("Token refresh failed: ${response.code}"))
            }

            // 새 토큰 저장
            tokenManager.saveTokens(
                accessToken = response.result.tokenResponse.accessToken,
                refreshToken = response.result.tokenResponse.refreshToken
            )

            Result.success(response.result.toDomainModel())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}