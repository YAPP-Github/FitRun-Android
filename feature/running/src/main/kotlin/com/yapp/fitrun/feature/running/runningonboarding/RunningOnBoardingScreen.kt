package com.yapp.fitrun.feature.running.runningonboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.fitrun.core.designsystem.Head_h2_semiBold
import com.yapp.fitrun.core.designsystem.R

@Composable
internal fun RunningOnBoardingRoute(
    onNavigateToReady: () -> Unit = {},
) {
    RunningOnBoardingScreen(
        onNavigateToReady = onNavigateToReady,
    )
}

@Composable
internal fun RunningOnBoardingScreen(
    onNavigateToReady: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.fg_nuetral_gray1000)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "러닝 온보딩",
            style = Head_h2_semiBold,
            color = Color.White,
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = onNavigateToReady,
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = "시작하기")
        }
    }
}

@Preview
@Composable
fun RunningOnBoardingScreenPreview() {
    RunningOnBoardingScreen()
}
