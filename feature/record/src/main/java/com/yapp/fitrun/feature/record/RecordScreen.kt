package com.yapp.fitrun.feature.record

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.yapp.fitrun.core.designsystem.Head_h1_bold
import com.yapp.fitrun.core.designsystem.R

@Composable
internal fun RecordRoute(
    padding: PaddingValues,
) {
    RecordScreen()
}

@Composable
internal fun RecordScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "Record",
            modifier = Modifier.align(Alignment.Center),
            color = colorResource(R.color.fg_text_primary),
            style = Head_h1_bold,
        )
    }
}
