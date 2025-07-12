package com.yapp.fitrun.feature.mypage

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
import com.yapp.fitrun.core.designsystem.Head_head1_bold
import com.yapp.fitrun.core.designsystem.TextPrimary

@Composable
internal fun MyPageRoute(
    padding: PaddingValues,
) {
    MyPageScreen()
}

@Composable
internal fun MyPageScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Text(
            text = "MyPage",
            modifier = Modifier.align(Alignment.Center),
            color = TextPrimary,
            style = Head_head1_bold,
        )
    }
}

@Preview
@Composable
fun MyPageScreenPreview() {
    MyPageScreen()
}