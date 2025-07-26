package com.yapp.fitrun.feature.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.yapp.fitrun.core.designsystem.Head_h1_bold
import com.yapp.fitrun.core.designsystem.R

@Composable
internal fun MyPageRoute(
    padding: PaddingValues,
) {
    MyPageScreen(
        padding = padding,
    )
}

@Composable
internal fun MyPageScreen(
    padding: PaddingValues,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(padding),
    ) {
        Text(
            text = "MyPage",
            modifier = Modifier.align(Alignment.Center),
            color = colorResource(R.color.fg_text_primary),
            style = Head_h1_bold,
        )
    }
}

@Preview
@Composable
fun MyPageScreenPreview() {
    MyPageScreen(PaddingValues())
}
