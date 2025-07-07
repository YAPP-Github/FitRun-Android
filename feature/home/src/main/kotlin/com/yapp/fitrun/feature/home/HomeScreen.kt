package com.yapp.fitrun.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.yapp.fitrun.core.designsystem.Head_head1_Bold
import com.yapp.fitrun.core.designsystem.TextPrimary

@Composable
internal fun HomeRoute(
    padding: PaddingValues,
) {
    HomeScreen()
}

@Composable
internal fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Text(
            text = "Home",
            modifier = Modifier.align(Alignment.Center),
            color = TextPrimary,
            style = Head_head1_Bold,
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}