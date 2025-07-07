package com.yapp.fitrun.core.network

import com.yapp.fitrun.core.network.model.response.LoginResponse
import com.yapp.fitrun.core.network.model.response.TokenResponse

interface AuthDataSource {
    suspend fun loginWithKakao(idToken: String): LoginResponse
    suspend fun logout()
    suspend fun updateRefreshToken(): TokenResponse
}
