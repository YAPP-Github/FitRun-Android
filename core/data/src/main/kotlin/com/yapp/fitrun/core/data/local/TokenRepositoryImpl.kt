package com.yapp.fitrun.core.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yapp.fitrun.core.domain.repository.TokenRepository
import com.yapp.fitrun.core.datastore.datasource.authDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
): TokenRepository {
    private companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

    // 토큰 Flow
    val accessToken: Flow<String?> = context.authDataStore.data
        .map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }

    val refreshToken: Flow<String?> = context.authDataStore.data
        .map { preferences ->
            preferences[REFRESH_TOKEN_KEY]
        }

    // 로그인 상태 확인
    val isLoggedIn: Flow<Boolean> = accessToken
        .map { token -> !token.isNullOrEmpty() }

    // 토큰 저장
    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        context.authDataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    // 전체 로그인 정보 저장 (토큰 + 사용자 정보)
    suspend fun saveLoginInfo(
        accessToken: String,
        refreshToken: String,
    ) {
        context.authDataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    // 액세스 토큰만 업데이트
    suspend fun updateAccessToken(accessToken: String) {
        context.authDataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
        }
    }

    // 동기적으로 토큰 가져오기 (Interceptor에서 사용)
    override suspend fun getAccessTokenSync(): String? {
        return accessToken.firstOrNull()
    }

    override suspend fun getRefreshTokenSync(): String? {
        return refreshToken.firstOrNull()
    }

    // 모든 데이터 삭제 (로그아웃)
    suspend fun clearAll() {
        context.authDataStore.edit { preferences ->
            preferences.clear()
        }
    }

    // 토큰만 삭제
    override suspend fun clearTokens() {
        context.authDataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
            preferences.remove(REFRESH_TOKEN_KEY)
        }
    }
}