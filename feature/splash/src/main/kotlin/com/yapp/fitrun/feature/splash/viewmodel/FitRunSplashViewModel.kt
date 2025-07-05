package com.yapp.fitrun.feature.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.fitrun.core.data.local.TokenRepositoryImpl
import com.yapp.fitrun.core.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FitRunSplashViewModel @Inject constructor(
    private val tokenRepositoryImpl: TokenRepositoryImpl,
    private val authRepository: AuthRepository
) : ViewModel(), ContainerHost<FitRunSplashState, FitRunSplashSideEffect> {

    override val container =
        container<FitRunSplashState, FitRunSplashSideEffect>(FitRunSplashState())

    init {
        // TODO: token validate logic

        checkAutoLogin()

        // 임시로 login fail 설정해둠
//        intent {
//            postSideEffect(FitRunSplashSideEffect.AutoLoginFail)
//        }
    }

    private fun checkAutoLogin() = intent {
        reduce { state.copy(isLoading = true) }

        // 최소 스플래시 표시 시간 (UX를 위해)
        delay(1500)

        // 토큰 존재 여부 확인
        val accessToken = tokenRepositoryImpl.getAccessTokenSync()
        val refreshToken = tokenRepositoryImpl.getRefreshTokenSync()

        when {
            accessToken.isNullOrEmpty() || refreshToken.isNullOrEmpty() -> {
                // 토큰이 없으면 로그인 화면으로
                reduce { state.copy(isLoading = false) }
                postSideEffect(FitRunSplashSideEffect.AutoLoginFail)
            }

            else -> {
                // 토큰이 있으면 유효성 검증
                postSideEffect(FitRunSplashSideEffect.ValidateToken)
                validateTokenAndLogin(refreshToken)
            }
        }
    }

    private fun validateTokenAndLogin(refreshToken: String) = intent {
        viewModelScope.launch {
            authRepository.refreshToken(refreshToken).fold(
                onSuccess = { loginResult ->
                    // 토큰 갱신 성공 - 자동 로그인 성공
                    reduce {
                        state.copy(
                            isLoading = false,
                        )
                    }

                    // 메인 화면으로 이동
                    intent {
                        postSideEffect(FitRunSplashSideEffect.AutoLoginSuccess)
                    }
                },
                onFailure = { error ->
                    // 토큰 갱신 실패 - 로그인 화면으로
                    reduce { state.copy(isLoading = false) }

                    // 토큰 만료 또는 유효하지 않음 - 모든 토큰 삭제
                    tokenRepositoryImpl.clearAll()

                    intent {
                        postSideEffect(FitRunSplashSideEffect.AutoLoginFail)
                    }
                }
            )
        }
    }
//
//    fun autoLoginFail() = intent {
//        viewModelScope.launch {
//            postSideEffect(FitRunSplashSideEffect.AutoLoginFail)
//        }
//    }
//
//    fun autoLoginSuccess() = intent {
//        postSideEffect(FitRunSplashSideEffect.AutoLoginSuccess)
//    }

}