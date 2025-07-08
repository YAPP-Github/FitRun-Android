package com.yapp.fitrun.feature.splash.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.fitrun.core.domain.repository.AuthRepository
import com.yapp.fitrun.core.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FitRunSplashViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val authRepository: AuthRepository
) : ViewModel(), ContainerHost<FitRunSplashState, FitRunSplashSideEffect> {

    override val container =
        container<FitRunSplashState, FitRunSplashSideEffect>(FitRunSplashState())

    init {
        checkAutoLogin()
    }

    private fun checkAutoLogin() = intent {
        // 최소 스플래시 표시 시간 (UX를 위해)
        delay(1500)

        viewModelScope.launch {
            // 토큰 존재 여부 확인
            val accessToken = tokenRepository.getAccessTokenSync()

            if (accessToken.isNotEmpty()) {
                Log.d(this@FitRunSplashViewModel.javaClass.name, "AccessToken: $accessToken")
                postSideEffect(FitRunSplashSideEffect.ValidateToken)
            }
            else {
//                postSideEffect(FitRunSplashSideEffect.AutoLoginFail)
                validateTokenAndLogin()
            }
        }
    }

    private fun validateTokenAndLogin() = intent {
        viewModelScope.launch {
            authRepository.updateRefreshToken().fold(
                onSuccess = { loginResult ->
                    // 토큰 갱신 성공 - 자동 로그인 성공
                    tokenRepository.setRefreshTokenSync(loginResult.refreshToken)
                    tokenRepository.setAccessTokenSync(loginResult.accessToken)
                    // 메인 화면으로 이동
                    intent {
                        postSideEffect(FitRunSplashSideEffect.AutoLoginSuccess)
                    }
                },
                onFailure = { error ->
                    // 토큰 갱신 실패 - 로그인 화면으로
                    // 토큰 만료 또는 유효하지 않음 - 모든 토큰 삭제
                    tokenRepository.clearTokens()
                    intent {
                        postSideEffect(FitRunSplashSideEffect.AutoLoginFail)
                    }
                }
            )
        }
    }

    fun autoLoginSuccess() = intent {
        postSideEffect(FitRunSplashSideEffect.AutoLoginSuccess)
    }

    fun autoLoginFail() = intent {
        viewModelScope.launch {
            tokenRepository.clear()
            postSideEffect(FitRunSplashSideEffect.AutoLoginFail)
        }
    }

}