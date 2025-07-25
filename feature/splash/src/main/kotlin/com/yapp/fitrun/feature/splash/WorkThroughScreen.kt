package com.yapp.fitrun.feature.splash

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.designsystem.Body_body3_medium
import com.yapp.fitrun.core.designsystem.Body_body3_semiBold
import com.yapp.fitrun.core.designsystem.Head_h1_bold
import com.yapp.fitrun.core.ui.FitRunTextButton
import kotlinx.coroutines.launch

@Composable
fun PagerIndicator(
    pageCount: Int,
    currentPage: Int,
    targetPage: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = colorResource(R.color.bg_interactive_secondary),
    indicatorSize: Dp = 8.dp,
    selectedIndicatorWidth: Dp = 28.dp,
    indicatorPadding: Dp = 5.dp,
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pageCount) { page ->
            val color =
                if (currentPage == page || targetPage == page) {
                    backgroundColor
                } else {
                    backgroundColor.copy(alpha = 0.4f)
                }

            Box(
                modifier = if (currentPage == page) {
                    Modifier
                        .padding(indicatorPadding)
                        .width(selectedIndicatorWidth)
                        .height(indicatorSize)
                        .clip(CircleShape)
                        .background(color)
                } else {
                    Modifier
                        .padding(indicatorPadding)
                        .size(indicatorSize)
                        .clip(CircleShape)
                        .background(color)
                },
            )
        }
    }
}

@Composable
internal fun WorkThroughScreen(
    titleTextList: List<Int>,
    descriptionTextList: List<Int>,
    onButtonClick: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 3 })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White),
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        PagerIndicator(
            pageCount = pagerState.pageCount,
            currentPage = pagerState.currentPage,
            targetPage = pagerState.currentPage,
        )

        Spacer(modifier = Modifier.height(70.dp))
        HorizontalPager(state = pagerState) { page ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = titleTextList[page]),
                    textAlign = TextAlign.Center,
                    color = colorResource(R.color.fg_text_primary),
                    style = Head_h1_bold,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = descriptionTextList[page]),
                    textAlign = TextAlign.Center,
                    color = colorResource(R.color.fg_text_secondary),
                    style = Body_body3_medium,
                )
                // TODO: Image
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        FitRunTextButton(
            modifier = Modifier
                .systemBarsPadding()
                .padding(start = 20.dp, end = 20.dp, bottom = 25.dp),
            onClick = {
                if (pagerState.currentPage == 2) {
                    onButtonClick()
                } else {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
            text =
            if (pagerState.currentPage == 2)
                stringResource(id = R.string.work_through_start)
            else
                stringResource(id = R.string.work_through_next),
            textColor = colorResource(R.color.fg_text_interactive_inverse),
            textStyle = Body_body3_semiBold,
            buttonColor = colorResource(R.color.bg_interactive_secondary),
        )
    }
}

@Preview
@Composable
internal fun PagerIndicatorPreview() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    PagerIndicator(
        pageCount = pagerState.pageCount,
        currentPage = pagerState.currentPage,
        targetPage = pagerState.currentPage,
    )
}

@Preview
@Composable
fun WorkThroughScreenPreview() {
    WorkThroughScreen(
        titleTextList = listOf(R.string.work_through_1_title),
        descriptionTextList = listOf(R.string.work_through_1_description),
        onButtonClick = {},
    )
}
