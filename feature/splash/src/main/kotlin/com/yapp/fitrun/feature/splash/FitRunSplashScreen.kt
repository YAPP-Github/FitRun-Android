package com.yapp.fitrun.feature.splash

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.fitrun.feature.splash.viewmodel.FitRunSplashViewModel
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.feature.splash.viewmodel.FitRunSplashSideEffect
import org.orbitmvi.orbit.compose.collectSideEffect
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.yapp.fitrun.feature.splash.viewmodel.FitRunSplashState
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun FitRunsSplashRoute(
    navigateToLogin: () -> Unit,
    navigateToMain: () -> Unit,
    viewModel: FitRunSplashViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    // Side Effects 처리
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is FitRunSplashSideEffect.ValidateToken -> {
                if (AuthApiClient.instance.hasToken()) {
                    Log.d("FitRunSplashRoute", "accessToken is exists")

                    UserApiClient.instance.accessTokenInfo { _, error ->
                        if (error != null) {
                            if (error is KakaoSdkError && error.isInvalidTokenError()) {
                                Log.e("FitRunSplashRoute", "invalid accessToken!")
                                viewModel.autoLoginFail()
                            }
                            else {
                                Log.e("FitRunSplashRoute", "error!")
                                viewModel.autoLoginFail()
                            }
                        }
                        else {
                            Log.d("FitRunSplashRoute", "valid accessToken!")
                            viewModel.autoLoginSuccess()
                        }
                    }
                }
                else {
                    //로그인 필요
                    Log.e("FitRunSplashRoute", "accessToken is not exists")
                    viewModel.autoLoginFail()
                }
            }
            is FitRunSplashSideEffect.AutoLoginSuccess -> {
                navigateToMain()
            }
            is FitRunSplashSideEffect.AutoLoginFail -> {
                navigateToLogin()
            }
        }
    }

    FitRunSplashScreen(
        uiState = uiState,
        onClickWorkThroughStart = navigateToLogin
    )
}

@Composable
internal fun FitRunSplashScreen(
    uiState: FitRunSplashState,
    onClickWorkThroughStart: () -> Unit,
) {
    if (uiState.showSplash) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.bg_brand))
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_splash_text),
                contentDescription = "Splash Image",
                modifier = Modifier.align(Alignment.Center),
                contentScale = ContentScale.Fit,
            )
        }
    }

    if (uiState.showWorkThrough) {
        WorkThroughScreen(
            titleTextList = uiState.titleTextList,
            descriptionTextList = uiState.descriptionTextList,
            onButtonClick = onClickWorkThroughStart,
        )
    }
}

@Preview
@Composable
fun IntroScreenPreview() {
    FitRunSplashScreen(
        uiState = FitRunSplashState(),
        onClickWorkThroughStart = {},
    )
}
