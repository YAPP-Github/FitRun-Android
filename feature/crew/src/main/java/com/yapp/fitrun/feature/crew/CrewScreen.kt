package com.yapp.fitrun.feature.crew

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
internal fun CrewRoute(
    // TODO: viewmodel
    padding: PaddingValues,
) {
    CrewScreen(
        padding = padding,
    )
}

@Composable
internal fun CrewScreen(
    padding: PaddingValues,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding(),
            )
            .background(Color.White),
    ) {
        Text(
            text = "Crew",
            modifier = Modifier.align(Alignment.Center),
            color = colorResource(R.color.fg_text_primary),
            style = Head_h1_bold,
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    CrewScreen(
        padding = PaddingValues(),
    )
}
