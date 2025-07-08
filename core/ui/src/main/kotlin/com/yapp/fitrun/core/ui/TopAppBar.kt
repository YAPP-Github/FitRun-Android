package com.yapp.fitrun.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.fitrun.core.designsystem.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingTopAppBar(
    modifier: Modifier = Modifier,
    onLeftNavigationClick: () -> Unit = {},
    onRightNavigationClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.background(Color.White),
        title = {
            LinearProgressIndicator(
                modifier = Modifier.height(4.dp).width(192.dp),
                trackColor = Color(0xFFEEEFF1),
                color = Color(0xFFFF6600),
                strokeCap = StrokeCap.Round
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onLeftNavigationClick
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "navigation back icon"
                )
            }
        },
        actions = {
            IconButton(
                onClick = onRightNavigationClick
            ) {
                Text(
                    text = stringResource(R.string.on_boarding_next),
                    color = Color(0XFFB0B3BA)
                )
            }
        },
    )
}

@Preview
@Composable
fun ILabTopAppBarBackPreview() {
    OnBoardingTopAppBar()
}