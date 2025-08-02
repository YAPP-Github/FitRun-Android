package com.yapp.fitrun.feature.record

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PathOverlay
import com.yapp.fitrun.core.designsystem.Body_body1_bold
import com.yapp.fitrun.core.designsystem.Body_body2_semiBold
import com.yapp.fitrun.core.designsystem.Body_body3_regular
import com.yapp.fitrun.core.designsystem.Body_body4_medium
import com.yapp.fitrun.core.designsystem.Caption_caption2_bold
import com.yapp.fitrun.core.designsystem.Caption_caption2_semiBold
import com.yapp.fitrun.core.designsystem.Head_h1_semiBold
import com.yapp.fitrun.core.designsystem.Head_h2_bold
import com.yapp.fitrun.core.designsystem.Head_h4_bold
import com.yapp.fitrun.core.designsystem.Number_number2_bold
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.ui.FitRunTextIconButton
import com.yapp.fitrun.core.ui.NavigationTopAppBar
import com.yapp.fitrun.feature.record.viewmodel.RecordDetailState
import com.yapp.fitrun.feature.record.viewmodel.RecordDetailViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun RecordDetailRoute(
    onBackClick: () -> Unit,
    padding: PaddingValues,
    recordId: Int,
    viewModel: RecordDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()
    LaunchedEffect(recordId) {
        viewModel.loadRecordDetail(recordId)
    }

    viewModel.collectSideEffect { _ ->
    }

    RecordDetailScreen(
        onBackClick = onBackClick,
        uiState = uiState,
        padding = padding,
    )
}

@Composable
internal fun RecordDetailScreen(
    onBackClick: () -> Unit,
    uiState: RecordDetailState,
    padding: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding(),
            )
            .background(colorResource(R.color.bg_secondary))
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            NavigationTopAppBar(
                onLeftNavigationClick = onBackClick,
                leftNavigationIconTint = colorResource(R.color.fg_icon_primary),
                isRightIconVisible = false,
            )
        }

        // 상단 타이틀
        item {
            RecordDetailTitleSection(uiState.title)
        }

        // 목표 달성 섹션
        item {
            Spacer(modifier = Modifier.height(28.dp))
            RecordDetailGoalSection()
        }

        // 총 러닝 거리 섹션
        item {
            Spacer(modifier = Modifier.height(28.dp))
            RecordDetailInfoSection(uiState)
        }

        // 러닝 코스 섹션
        item {
            Spacer(modifier = Modifier.height(28.dp))
            RecordDetailRouteSection(uiState)
        }

        // 랩 구간 섹션
        item {
            Spacer(modifier = Modifier.height(28.dp))
            RecordDetailLabSection(uiState)
        }

        // 삭제 버튼
        item {
            Spacer(modifier = Modifier.height(48.dp))
            FitRunTextIconButton(
                modifier = Modifier.width(130.dp),
                onClick = {},
                text = "삭제하기",
                textColor = colorResource(R.color.fg_text_secondary),
                textStyle = Caption_caption2_bold,
                imageResource = painterResource(R.drawable.ic_trash),
                buttonColor = colorResource(R.color.fg_nuetral_gray400),
                iconModifier = Modifier.size(14.dp),
            )
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
internal fun RecordDetailTitleSection(
    title: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 36.dp)
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth(),
        ) {
            Text(
                text = title,
                style = Head_h2_bold,
                color = colorResource(R.color.fg_text_primary),
                modifier = Modifier.wrapContentWidth(),
            )

            Text(
                text = "2025/07/20 13:00:00",
                style = Body_body3_regular,
                color = colorResource(R.color.fg_text_secondary),
                modifier = Modifier.padding(top = 4.dp),
            )
        }

        Box(
            modifier = Modifier
                .size(44.dp)
                .clickable { },
        ) {
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd),
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = "set time goal",
                contentScale = ContentScale.Fit,
            )
        }
    }
}

@Composable
internal fun RecordDetailGoalSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(
                color = colorResource(R.color.bg_primary),
                shape = RoundedCornerShape(12.dp),
            )
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.padding(start = 20.dp),
            painter = painterResource(R.drawable.ic_medal),
            contentDescription = "set time goal",
            contentScale = ContentScale.Fit,
        )

        Text(
            text = "페이스, 거리, 시간 목표를 달성했어요!",
            style = Caption_caption2_semiBold,
            color = colorResource(R.color.fg_text_primary),
            modifier = Modifier.padding(start = 16.dp),
        )
    }
}

@Composable
internal fun RecordDetailInfoSection(
    uiState: RecordDetailState,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(
                color = colorResource(R.color.bg_primary),
                shape = RoundedCornerShape(12.dp),
            ),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = uiState.totalDistance.toString(),
                style = Number_number2_bold,
                color = colorResource(R.color.fg_text_primary),
            )
            Text(
                text = "km",
                style = Head_h1_semiBold,
                color = colorResource(R.color.fg_text_primary),
                modifier = Modifier.padding(start = 4.dp),
            )
        }

        Row(
            modifier = Modifier.padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.weight(0.5f),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "평균 페이스",
                    style = Body_body4_medium,
                    color = colorResource(R.color.fg_text_tertiary),
                )
                Text(
                    text = uiState.averagePace,
                    style = Body_body1_bold,
                    color = colorResource(R.color.fg_text_primary),
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
            Column(
                modifier = Modifier.weight(0.5f),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "러닝 시간",
                    style = Body_body4_medium,
                    color = colorResource(R.color.fg_text_tertiary),
                )
                Text(
                    text = uiState.totalTime,
                    style = Body_body1_bold,
                    color = colorResource(R.color.fg_text_primary),
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
internal fun RecordDetailRouteSection(
    uiState: RecordDetailState,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(
                color = colorResource(R.color.bg_primary),
                shape = RoundedCornerShape(12.dp),
            ),
    ) {
        Text(
            text = "러닝 코스",
            style = Head_h4_bold,
            color = colorResource(R.color.fg_text_primary),
            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
        )
        Text(
            text = "종로구 서울특별시 대한민국",
            style = Body_body4_medium,
            color = colorResource(R.color.fg_text_secondary),
            modifier = Modifier.padding(start = 20.dp, top = 4.dp),
        )

        Box(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .width(295.dp)
                .height(150.dp)
                .align(Alignment.CenterHorizontally)
                .background(
                    color = colorResource(R.color.bg_secondary),
                    shape = RoundedCornerShape(12.dp),
                ),
        ) {
            NaverMap(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.Unspecified,
                    ),
                uiSettings = MapUiSettings(
                    isZoomControlEnabled = false,
                    isCompassEnabled = false,
                    isScaleBarEnabled = false,
                    isLocationButtonEnabled = false,
                    isScrollGesturesEnabled = true,
                    isZoomGesturesEnabled = true,
                    isRotateGesturesEnabled = false,
                    isTiltGesturesEnabled = false,
                ),
            ) {
                PathOverlay(
                    coords = uiState.runningPoint,
                    width = 4.dp,
                    outlineWidth = 0.dp,
                    color = colorResource(R.color.bg_interactive_primary),
                )
            }
        }
    }
}

@Composable
internal fun RecordDetailLabSection(
    uiState: RecordDetailState,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(
                color = colorResource(R.color.bg_primary),
                shape = RoundedCornerShape(12.dp),
            ),
    ) {
        Text(
            text = "랩 구간",
            style = Head_h4_bold,
            color = colorResource(R.color.fg_text_primary),
            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 20.dp),
        ) {
            Text(
                text = "km",
                style = Body_body4_medium,
                color = colorResource(R.color.fg_text_secondary),
                modifier = Modifier.size(20.dp),
                textAlign = TextAlign.Start,
            )

            Text(
                text = "평균 페이스",
                style = Body_body4_medium,
                color = colorResource(R.color.fg_text_secondary),
                modifier = Modifier.padding(start = 35.dp),
            )
        }

        repeat(uiState.segments.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${index + 1}",
                    style = Body_body4_medium,
                    color = colorResource(R.color.fg_text_secondary),
                    modifier = Modifier.size(20.dp),
                    textAlign = TextAlign.Center,
                )

                Box(
                    modifier = Modifier
                        .padding(start = 35.dp)
                        .width(212.dp)
                        .height(44.dp)
                        .background(
                            color = colorResource(R.color.fg_nuetral_gray600),
                            shape = RoundedCornerShape(10.dp),
                        ),
                ) {
                    Text(
                        text = uiState.segments[index].averagePace,
                        style = Body_body2_semiBold,
                        color = colorResource(R.color.fg_text_interactive_inverse),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .align(Alignment.CenterStart),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun RecordDetailScreenPreview() {
    RecordDetailScreen(
        padding = PaddingValues(0.dp),
        uiState = RecordDetailState(),
        onBackClick = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun RecordDetailLabSectionPreview() {
    RecordDetailLabSection(
        uiState = RecordDetailState(),
    )
}
