package com.yapp.fitrun.feature.mypage.setting

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.fitrun.core.designsystem.Body_body3_bold
import com.yapp.fitrun.core.designsystem.Body_body3_semiBold
import com.yapp.fitrun.core.designsystem.Head_h1_bold
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.designsystem.pretendardFamily
import com.yapp.fitrun.core.ui.FitRunTextButton
import com.yapp.fitrun.core.ui.FitRunTopAppBar
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageState
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun ChangeRunningTimeDistanceGoalRoute(
    initialPage: Int,
    padding: PaddingValues,
    onBackClick: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val state by viewModel.collectAsState()

    ChangeRunningTimeDistanceGoalScreen(
        uiState = state,
        initialPage = initialPage,
        padding = padding,
        onBackClick = onBackClick,
        onClickChangeRunningTimeGoal = viewModel::onClickChangeRunningTimeGoal,
        onClickChangeRunningDistanceGoal = viewModel::onClickChangeRunningDistanceGoal,
    )
}

@Composable
internal fun ChangeRunningTimeDistanceGoalScreen(
    uiState: MyPageState,
    initialPage: Int,
    padding: PaddingValues,
    onBackClick: () -> Unit,
    onClickChangeRunningTimeGoal: (String) -> Unit,
    onClickChangeRunningDistanceGoal: (String) -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = initialPage, pageCount = { 2 })
    val timeGoalValue = remember { mutableStateOf(uiState.userGoalTime) }
    val distanceGoalValue = remember { mutableStateOf(uiState.userGoalDistance) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_primary)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FitRunTopAppBar(
            onLeftNavigationClick = onBackClick,
        )

        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = 2,
            titles = listOf("목표 시간", "목표 거리"),
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp),
        )
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
        ) { page ->
            when (page) {
                0 -> SettingGoalSection(
                    unit = "분",
                    timeGoalValue,
                )

                1 -> SettingGoalSection(
                    unit = "km",
                    distanceGoalValue,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        FitRunTextButton(
            modifier = Modifier
                .padding(bottom = padding.calculateBottomPadding())
                .padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
                .imePadding(),
            onClick = {
                if (pagerState.currentPage == 0)
                    onClickChangeRunningTimeGoal(timeGoalValue.value)
                else
                    onClickChangeRunningDistanceGoal(distanceGoalValue.value)
            },
            text = "설정하기",
        )
    }

}

@Composable
internal fun SettingGoalSection(
    unit: String,
    goalValue: MutableState<String>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(top = 64.dp),
            text = "일주일에",
            color = colorResource(R.color.fg_text_secondary),
            style = Body_body3_semiBold,
        )

        Row(
            modifier = Modifier.wrapContentSize(),
        ) {
            BasicTextField(
                modifier = Modifier.width(IntrinsicSize.Min),
                value = goalValue.value,
                onValueChange = {
                    goalValue.value = it
                },
                textStyle = TextStyle(
                    textAlign = TextAlign.End,
                    fontFamily = pretendardFamily,
                    fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
                    fontSize = 52.sp,
                    color = colorResource(R.color.fg_text_primary),
                    lineHeight = dimensionResource(id = R.dimen.line_height_1700).value.sp,
                    letterSpacing = dimensionResource(id = R.dimen.number_letter_spacing).value.sp,
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.None,
                    ),
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                decorationBox = { innerTextField ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        horizontalAlignment = Alignment.End,
                    ) {
                        innerTextField()
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(colorResource(R.color.bg_interactive_primary)),
                        )
                    }
                },
            )

            Text(
                text = unit,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 10.dp),
                textAlign = TextAlign.Center,
                color = colorResource(R.color.fg_text_tertiary),
                style = Head_h1_bold,
            )
        }
    }
}


@SuppressLint("UnusedBoxWithConstraintsScope", "UseOfNonLambdaOffsetOverload")
@Composable
internal fun HorizontalPagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    titles: List<String>,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = modifier
            .background(
                color = colorResource(R.color.fg_nuetral_gray100),
                shape = RoundedCornerShape(100f),
            )
            .height(46.dp)
            .padding(4.dp)
            .fillMaxWidth(),
    ) {
        val tabWidth = maxWidth / pageCount

        val animatedOffset by animateDpAsState(
            targetValue = tabWidth * pagerState.currentPage,
            animationSpec = tween(durationMillis = 300),
            label = "PagerIndicatorAnimation",
        )

        Box(
            modifier = Modifier
                .offset(x = animatedOffset)
                .width(tabWidth)
                .height(46.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(100.dp),
                    clip = false,
                )
                .clip(RoundedCornerShape(100.dp))
                .background(colorResource(R.color.base_white)),
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            repeat(pageCount) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(100f))
                        .clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = titles[index],
                        color = if (pagerState.currentPage == index) {
                            colorResource(R.color.fg_text_primary)
                        } else {
                            colorResource(R.color.fg_nuetral_gray700)
                        },
                        style = Body_body3_bold,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
internal fun ChangeRunningTimeDistanceGoalScreenPreview() {
    ChangeRunningTimeDistanceGoalScreen(
        uiState = MyPageState(),
        initialPage = 0,
        padding = PaddingValues(),
        onBackClick = {},
        onClickChangeRunningTimeGoal = {},
        onClickChangeRunningDistanceGoal = {},
    )
}
