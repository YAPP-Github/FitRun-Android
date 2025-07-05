package com.yapp.fitrun.feature.login.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.yapp.fitrun.core.domain.model.User
import com.yapp.fitrun.core.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authRepository: AuthRepository
) : ViewModel(), ContainerHost<LoginState, LoginSideEffect> {

    override val container = container<LoginState, LoginSideEffect>(LoginState())

    fun onLoginClick() = intent {
        reduce { state.copy(isLoading = true) }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                handleKakaoLoginResult(token, error)
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                handleKakaoLoginResult(token, error)
            }
        }
    }

    private fun handleKakaoLoginResult(token: OAuthToken?, error: Throwable?) = intent {
        if (error != null) {
            reduce { state.copy(isLoading = false) }

            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                postSideEffect(LoginSideEffect.ShowError("로그인이 취소되었습니다"))
            } else {
                postSideEffect(LoginSideEffect.ShowError("로그인 실패: ${error.message}"))
            }
        } else if (token != null) {
            fetchIdTokenAndUserInfo(token)
        }
    }

    private fun fetchIdTokenAndUserInfo(oAuthToken: OAuthToken) = intent {
        try {
            // ID Token 가져오기 (OIDC 필요)
            val idToken =
                oAuthToken.idToken ?: oAuthToken.accessToken // idToken이 없으면 accessToken 사용

            // 사용자 정보 요청
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    intent {
                        reduce { state.copy(isLoading = false) }
                        postSideEffect(LoginSideEffect.ShowError("사용자 정보 요청 실패: ${error.message}"))
                    }
                } else if (user != null) {
                    val nickname = user.kakaoAccount?.profile?.nickname ?: ""
                    sendLoginToServer(idToken, nickname)
                }
            }
        } catch (e: Exception) {
            reduce { state.copy(isLoading = false) }
            postSideEffect(LoginSideEffect.ShowError("로그인 처리 중 오류: ${e.message}"))
        }
    }

    private fun sendLoginToServer(idToken: String, nickname: String) = intent {
        authRepository.loginWithKakao(
            idToken = idToken,
            nickname = nickname
        ).fold(
            onSuccess = { loginResponse ->
                reduce {
                    state.copy(
                        isLoading = false,
                        user = User(
                            id = loginResponse.user.id,
                            name = loginResponse.user.name,
                            email = loginResponse.user.email,
                            profileImage = loginResponse.user.profileImage,
                            provider = "KAKAO"
                        )
                    )
                }

                if (loginResponse.isNewUser) {
                    postSideEffect(LoginSideEffect.NavigateToOnboarding(loginResponse.user.id))
                } else {
                    postSideEffect(LoginSideEffect.NavigateToMain(loginResponse.user.id))
                }
            },
            onFailure = { throwable ->
                reduce { state.copy(isLoading = false) }
                println("@@@@ $throwable")
                postSideEffect(LoginSideEffect.ShowError(throwable.message ?: "서버 로그인 실패"))
            }
        )
    }
}
