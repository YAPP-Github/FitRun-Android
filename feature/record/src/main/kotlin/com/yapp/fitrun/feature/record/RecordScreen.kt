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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.fitrun.core.designsystem.Body_body1_bold
import com.yapp.fitrun.core.designsystem.Body_body2_semiBold
import com.yapp.fitrun.core.designsystem.Body_body3_bold
import com.yapp.fitrun.core.designsystem.Body_body4_medium
import com.yapp.fitrun.core.designsystem.Body_body4_semiBold
import com.yapp.fitrun.core.designsystem.Caption_caption2_bold
import com.yapp.fitrun.core.designsystem.Head_h1_semiBold
import com.yapp.fitrun.core.designsystem.Head_h4_bold
import com.yapp.fitrun.core.designsystem.Head_h6_semiBold
import com.yapp.fitrun.core.designsystem.Number_number2_bold
import com.yapp.fitrun.core.designsystem.Number_number3_bold
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.feature.record.viewmodel.Record
import com.yapp.fitrun.feature.record.viewmodel.RecordSideEffect
import com.yapp.fitrun.feature.record.viewmodel.RecordState
import com.yapp.fitrun.feature.record.viewmodel.RecordViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun RecordRoute(
    padding: PaddingValues,
    onNavigateToRecordDetail: (Int) -> Unit,
    viewModel: RecordViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is RecordSideEffect.OnNavigateToRecordDetail -> {
                onNavigateToRecordDetail(sideEffect.recordId)
            }
        }
    }

    RecordScreen(
        uiState = uiState,
        padding = padding,
        onNavigateToRecordDetail = onNavigateToRecordDetail,
    )
}

@Composable
internal fun RecordScreen(
    uiState: RecordState,
    padding: PaddingValues,
    onNavigateToRecordDetail: (Int) -> Unit,
) {
    if (uiState.recordCount == 0) {
        NoRecordDataView()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.bg_primary))
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 20.dp),
        ) {
            // 상단 타이틀
            item {
                Text(
                    text = "기록",
                    style = Head_h4_bold,
                    color = colorResource(R.color.fg_text_primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                )
            }
            // 총 러닝 거리 섹션
            item {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp),
                ) {
                    Text(
                        text = "총 러닝 거리",
                        style = Body_body4_medium,
                        color = colorResource(R.color.fg_text_tertiary),
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(
                        modifier = Modifier.wrapContentSize(),
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
                }
            }

            // 통계 정보 섹션
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                ) {
                    StatisticItem(
                        label = "러닝 횟수",
                        value = "${uiState.recordCount} 회",
                        modifier = Modifier,
                    )

                    StatisticItem(
                        label = "평균 페이스",
                        value = uiState.averagePace,
                        modifier = Modifier.padding(start = 24.dp),
                    )

                    StatisticItem(
                        label = "러닝 시간",
                        value = uiState.totalTime,
                        modifier = Modifier.padding(start = 24.dp),
                    )
                }
            }

            // 목표 달성 횟수
            item {
                Text(
                    text = "목표 달성 횟수",
                    style = Body_body4_medium,
                    color = colorResource(R.color.fg_text_tertiary),
                    modifier = Modifier.padding(top = 32.dp),
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = 8.dp)
                        .background(
                            colorResource(R.color.bg_secondary),
                            shape = RoundedCornerShape(16.dp),
                        ),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                modifier = Modifier
                                    .width(29.dp)
                                    .height(20.dp),
                                painter = painterResource(R.drawable.ic_running_track),
                                contentDescription = "set time goal",
                                contentScale = ContentScale.Fit,
                            )
                            Text(
                                text = "거리",
                                style = Body_body4_semiBold,
                                color = colorResource(R.color.fg_text_primary),
                                modifier = Modifier.padding(start = 4.dp),
                            )

                            Spacer(modifier = Modifier.width(49.dp))

                            Text(
                                text = uiState.distanceGoalAchievedCount.toString(),
                                style = Body_body3_bold,
                                color = colorResource(R.color.fg_text_primary),
                            )

                            Text(
                                text = "회",
                                style = Caption_caption2_bold,
                                color = colorResource(R.color.fg_text_primary),
                                modifier = Modifier.padding(start = 2.dp),
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))
                        Spacer(
                            modifier = Modifier
                                .width(1.dp)
                                .height(22.dp)
                                .background(colorResource(R.color.fg_nuetral_gray500)),
                        )
                        Spacer(modifier = Modifier.width(12.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                modifier = Modifier
                                    .width(29.dp)
                                    .height(20.dp),
                                painter = painterResource(R.drawable.ic_running_clock),
                                contentDescription = "set time goal",
                                contentScale = ContentScale.Fit,
                            )

                            Text(
                                text = "시간",
                                style = Body_body4_semiBold,
                                color = colorResource(R.color.fg_text_primary),
                                modifier = Modifier.padding(start = 4.dp),
                            )

                            Spacer(modifier = Modifier.width(49.dp))

                            Text(
                                text = uiState.timeGoalAchievedCount.toString(),
                                style = Body_body3_bold,
                                color = colorResource(R.color.fg_text_primary),
                            )

                            Text(
                                text = "회",
                                style = Caption_caption2_bold,
                                color = colorResource(R.color.fg_text_primary),
                                modifier = Modifier.padding(start = 2.dp),
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(46.dp))
            }

            // 러닝 기록 리스트
            itemsIndexed(uiState.recordList) { _, record ->
                RunningRecordCard(
                    record = record,
                    onNavigateToRecordDetail = { onNavigateToRecordDetail(record.recordId) },
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
private fun NoRecordDataView() {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = "기록",
            style = Head_h4_bold,
            color = colorResource(R.color.fg_text_primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp, start = 20.dp),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.bg_secondary)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_no_data),
                contentDescription = "set time goal",
                contentScale = ContentScale.Fit,
            )

            Text(
                text = "기록이 아직 없어요",
                style = Head_h4_bold,
                color = colorResource(R.color.fg_text_primary),
                modifier = Modifier.padding(top = 21.dp),
            )

            Text(
                text = "지금 바로 달리기를 시작해보세요!",
                style = Body_body4_medium,
                color = colorResource(R.color.fg_text_secondary),
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@Composable
private fun StatisticItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = label,
            style = Body_body4_medium,
            color = colorResource(R.color.fg_text_tertiary),
            textAlign = TextAlign.Center,
        )

        Text(
            text = value,
            style = Body_body1_bold,
            color = colorResource(R.color.fg_text_primary),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 2.dp),
        )
    }
}

@Composable
private fun RunningRecordCard(
    record: Record,
    modifier: Modifier = Modifier,
    onNavigateToRecordDetail: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNavigateToRecordDetail() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.bg_primary),
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(164.dp)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = "${record.startAt} 시간 러닝",
                        style = Head_h6_semiBold,
                        color = colorResource(R.color.fg_text_tertiary),
                    )
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "${record.totalDistance}",
                            style = Number_number3_bold,
                            color = colorResource(R.color.fg_text_primary),
                        )

                        Text(
                            text = "km",
                            style = Body_body2_semiBold,
                            color = colorResource(R.color.fg_text_primary),
                            modifier = Modifier.padding(start = 2.dp),
                        )
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.dummy_map),
                    contentDescription = "Running Route",
                    modifier = Modifier
                        .width(80.dp)
                        .height(60.dp),
                    contentScale = ContentScale.Crop,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
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
                        text = record.averagePace,
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
                        text = record.totalTime,
                        style = Body_body1_bold,
                        color = colorResource(R.color.fg_text_primary),
                        modifier = Modifier.padding(top = 4.dp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecordScreenPreview() {
    RecordScreen(
        padding = PaddingValues(0.dp),
        uiState = RecordState(recordList = mutableListOf(Record())),
        onNavigateToRecordDetail = {},
    )
}
