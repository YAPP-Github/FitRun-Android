package com.yapp.fitrun.feature.workthrough

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.fitrun.core.designsystem.InteractiveSecondary
import com.yapp.fitrun.feature.workthrough.viewmodel.WorkThroughViewModel
import com.yapp.fitrun.core.design_system.R
import com.yapp.fitrun.core.designsystem.Body_body3_medium
import com.yapp.fitrun.core.designsystem.Body_body3_semiBold
import com.yapp.fitrun.core.designsystem.Head_head1_Bold
import com.yapp.fitrun.core.designsystem.InteractiveInverse
import com.yapp.fitrun.core.designsystem.TextPrimary
import com.yapp.fitrun.core.designsystem.TextSecondary
import com.yapp.fitrun.feature.workthrough.viewmodel.WorkThroughSideEffect
import kotlinx.coroutines.launch

@Composable
internal fun WorkThroughRoute(
    navigateToLogin: () -> Unit,
    viewModel: WorkThroughViewModel = hiltViewModel(),
) {
    val uiState by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { sideEffect ->
            when(sideEffect) {
                is WorkThroughSideEffect.OnClickStartButton -> {

                }
            }
        }
    }

    WorkThroughScreen(
        titleTextList = uiState.titleTextList,
        descriptionTextList = uiState.descriptionTextList,
        onButtonClick = viewModel::onStartClick,
    )
}

@Composable
fun PagerIndicator(
    pageCount: Int,
    currentPage: Int,
    targetPage: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = InteractiveSecondary,
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
            .background(Color.White)
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
                    color = TextPrimary,
                    style = Head_head1_Bold,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = descriptionTextList[page]),
                    textAlign = TextAlign.Center,
                    color = TextSecondary,
                    style = Body_body3_medium,
                )
                //TODO: Image
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 25.dp)
                .height(56.dp),
            enabled = true,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = InteractiveSecondary,
                contentColor = InteractiveSecondary,
            ),
            onClick = {
                if (pagerState.currentPage == 2) {
                    onButtonClick()
                }
                else {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
        ) {
            Text(
                text =
                    if (pagerState.currentPage == 2)
                        stringResource(id = R.string.work_through_start)
                    else
                        stringResource(id = R.string.work_through_next),
                textAlign = TextAlign.Center,
                color = InteractiveInverse,
                style = Body_body3_semiBold,
            )
        }
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
        onButtonClick = {}
    )
}