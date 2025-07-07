package com.yapp.fitrun.feature.login.viewmodel


import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.yapp.fitrun.core.domain.repository.AuthRepository
import com.yapp.fitrun.core.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) : ViewModel(), ContainerHost<LoginState, LoginSideEffect> {

    override val container = container<LoginState, LoginSideEffect>(LoginState())

    fun onLoginClick() = intent {
        postSideEffect(LoginSideEffect.KakaoLogin)
        reduce { state.copy(isLoading = true) }
    }

    fun handleKakaoLoginResult(token: OAuthToken?, error: Throwable?) = intent {
        if (error != null) {
            reduce { state.copy(isLoading = false) }

            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                postSideEffect(LoginSideEffect.LoginFail("로그인이 취소되었습니다"))
            } else {
                postSideEffect(LoginSideEffect.LoginFail("로그인 실패: ${error.message}"))
            }
        } else if (token != null) {
            fetchIdTokenAndUserInfo(token)
        }
    }

    private fun fetchIdTokenAndUserInfo(oAuthToken: OAuthToken) = intent {
        try {
            // ID Token 가져오기 (OIDC 필요)
            val idToken = oAuthToken.idToken ?: oAuthToken.accessToken // idToken이 없으면 accessToken 사용

            // TODO: 디버깅 용으로 일단 심어둠
            println("idToken: " + oAuthToken.idToken)
            println("accessToken: " + oAuthToken.accessToken)

            sendLoginToServer(idToken)
        } catch (e: Exception) {
            reduce { state.copy(isLoading = false) }
            postSideEffect(LoginSideEffect.LoginFail("로그인 처리 중 오류: ${e.message}"))
        }
    }

    private fun sendLoginToServer(idToken: String) = intent {
        reduce { state.copy(isLoading = true) }

        authRepository.loginWithKakao(idToken = idToken)
            .onSuccess { loginResponse ->
                tokenRepository.setAccessTokenSync(loginResponse.tokenEntity.accessToken)
                tokenRepository.setRefreshTokenSync(loginResponse.tokenEntity.refreshToken)

                reduce { state.copy(isLoading = false) }

                postSideEffect(LoginSideEffect.NavigateToMain(isNew = loginResponse.isNewUser))

            }.onFailure { throwable ->
                reduce { state.copy(isLoading = false) }
                println("@@@@ $throwable")
                postSideEffect(LoginSideEffect.LoginFail(throwable.message ?: "서버 로그인 실패"))
            }
    }
}
