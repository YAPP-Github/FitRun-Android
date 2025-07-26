package com.yapp.fitrun.feature.setgoal

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.yapp.fitrun.core.designsystem.Body_body2_bold
import com.yapp.fitrun.core.designsystem.Body_body3_semiBold
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.ui.NavigationTopAppBar
import com.yapp.fitrun.feature.setgoal.component.SetPaceSection
import com.yapp.fitrun.feature.setgoal.component.SetRunningCountSection
import kotlinx.coroutines.launch

@Composable
fun SetGoalRoute() {



    SetGoalScreen()
}

@Composable
fun SetGoalScreen() {
    var showLottie by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { 2 })

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.imePadding(),
            topBar = { NavigationTopAppBar() },
            bottomBar = {
                Button(
                    onClick = {
                        showLottie = true
                    },
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .fillMaxWidth()
                        .height(56.dp)
                        .imePadding(),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.bg_interactive_primary)
                    )
                ) {
                    Text(
                        text = "루틴 설정하기",
                        color = colorResource(R.color.fg_text_interactive_inverse),
                        style = Body_body3_semiBold
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(colorResource(R.color.base_white)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    pageCount = 2,
                    titles = listOf("페이스", "러닝횟수"),
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp),
                )
                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false
                ) { page ->
                    when (page) {
                        0 -> SetPaceSection()
                        1 -> SetRunningCountSection()
                    }
                }
            }
        }
        if (showLottie) {
            LottieAnimationOverlay(
                onAnimationEnd = {
                    showLottie = false
                }
            )
        }
    }
}

@Composable
fun LottieAnimationOverlay(
    onAnimationEnd: () -> Unit
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("toast_set_goal_completed.json") // 로티 파일 이름
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        isPlaying = true,
        restartOnPlay = true
    )

    // 애니메이션 완료 감지
    LaunchedEffect(progress) {
        if (progress == 1f) {
            onAnimationEnd()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .clickable(enabled = false) { }, // 배경 클릭 방지
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
        )
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    titles: List<String>,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = modifier
            .background(
                color = colorResource(R.color.fg_nuetral_gray100),
                shape = RoundedCornerShape(100f)
            )
            .padding(4.dp)
            .height(38.dp)
            .fillMaxWidth()
    ) {
        val tabWidth = maxWidth / pageCount

        // 현재 페이지를 기반으로 애니메이션 적용
        val animatedOffset by animateDpAsState(
            targetValue = tabWidth * pagerState.currentPage,
            animationSpec = tween(durationMillis = 300),
            label = "PagerIndicatorAnimation"
        )

        // 선택된 탭 배경
        Box(
            modifier = Modifier
                .offset(x = animatedOffset)
                .width(tabWidth)
                .height(38.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(100.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(100.dp))
                .background(colorResource(R.color.base_white))
        )


        // 페이지 버튼들
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
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
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = titles[index],
                        color = if (pagerState.currentPage == index) colorResource(R.color.fg_text_primary) else colorResource(
                            R.color.fg_nuetral_gray700
                        ),
                        style = Body_body2_bold
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SetGoalScreenPreview() {
    SetGoalScreen()
}
