package com.yapp.fitrun.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.fitrun.feature.splash.viewmodel.FitRunSplashViewModel
import com.yapp.fitrun.core.design_system.R
import com.yapp.fitrun.feature.splash.viewmodel.FitRunSplashSideEffect
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import androidx.compose.runtime.getValue

@Composable
internal fun FitRunsSplashRoute(
    navigateToLogin: () -> Unit,
    navigateToMain: () -> Unit,
    viewModel: FitRunSplashViewModel = hiltViewModel(),
) {
    val state by viewModel.collectAsState()

    // Side Effects 처리
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is FitRunSplashSideEffect.ValidateToken -> {
                // 토큰 검증 중 - UI 상태 업데이트 등
            }
            is FitRunSplashSideEffect.AutoLoginSuccess -> {
                navigateToMain()
            }
            is FitRunSplashSideEffect.AutoLoginFail -> {
                navigateToLogin()
            }
        }
    }

    FitRunSplashScreen()
}

@Composable
internal fun FitRunSplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_logo),
            contentDescription = "Splash Image",
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.Center),
            contentScale = ContentScale.Fit,
        )
    }
}

@Preview
@Composable
fun IntroScreenPreview() {
    FitRunSplashScreen()
}
