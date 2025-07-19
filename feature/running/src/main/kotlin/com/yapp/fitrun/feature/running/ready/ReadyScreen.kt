package com.yapp.fitrun.feature.running.ready

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.yapp.fitrun.core.designsystem.R

@Composable
internal fun ReadyRoute(
    onNavigateToPlay: () -> Unit = {},
) {
    val viewModel: ReadyViewModel = hiltViewModel()

    ReadyScreen(
        onNavigateToPlay = onNavigateToPlay
    )
}

@Composable
internal fun ReadyScreen(
    onNavigateToPlay: () -> Unit = {},
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("countdown.json"))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        speed = 1.0f,
        restartOnPlay = false,
        isPlaying = true,
    )

    LaunchedEffect(progress) {
        if (progress == 1f){
            onNavigateToPlay()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.fg_nuetral_gray1000))
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.fillMaxSize()
        )
    }
}

