package com.yapp.fitrun.feature.splash.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.domain.repository.AuthRepository
import com.yapp.fitrun.core.domain.repository.TokenRepository
import com.yapp.fitrun.core.domain.repository.WorkThroughRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import org.orbitmvi.orbit.syntax.simple.reduce

@HiltViewModel
class FitRunSplashViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val authRepository: AuthRepository,
    private val workThroughRepository: WorkThroughRepository,
) : ViewModel(), ContainerHost<FitRunSplashState, FitRunSplashSideEffect> {

    override val container =
        container<FitRunSplashState, FitRunSplashSideEffect>(FitRunSplashState())

    private val titleTextList: List<Int> = listOf(
        R.string.work_through_1_title,
        R.string.work_through_2_title,
        R.string.work_through_3_title,
    )
    private val descriptionTextList: List<Int> = listOf(
        R.string.work_through_1_description,
        R.string.work_through_2_description,
        R.string.work_through_3_description,
    )

    private val imageList: List<Int> = listOf(
        R.drawable.img_workthrough1,
        R.drawable.img_workthrough2,
        R.drawable.img_workthrough3,
    )

    init {
        checkIsInstalledBefore()
    }

    private fun checkIsInstalledBefore() = intent {
        reduce { state.copy(showSplash = true, showWorkThrough = false) }

        // 최소 스플래시 표시 시간 (UX를 위해)
        delay(1000)
        workThroughRepository.getIsFirstTime().let {
            if (it) {
                reduce {
                    state.copy(
                        showSplash = false,
                        showWorkThrough = true,
                        titleTextList = titleTextList,
                        descriptionTextList = descriptionTextList,
                        imageList = imageList,
                    )
                }
                workThroughRepository.setIsFirstTime(false)
            } else {
                checkAutoLogin()
            }
        }
    }

    private fun checkAutoLogin() = intent {
        viewModelScope.launch {
            // 토큰 존재 여부 확인
            val accessToken = tokenRepository.getAccessTokenSync()

            if (accessToken.isNotEmpty()) {
                Log.d(this@FitRunSplashViewModel.javaClass.name, "AccessToken: $accessToken")
                postSideEffect(FitRunSplashSideEffect.ValidateToken)
            } else {
                validateTokenAndLogin()
            }
        }
    }

    fun validateTokenAndLogin() = intent {
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
                },
            )
        }
    }

    fun autoLoginFail() = intent {
        viewModelScope.launch {
            tokenRepository.clear()
            postSideEffect(FitRunSplashSideEffect.AutoLoginFail)
        }
    }
}
