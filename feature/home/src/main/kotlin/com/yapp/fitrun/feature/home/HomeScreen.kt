package com.yapp.fitrun.feature.home

import android.Manifest
import android.location.Location
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.overlay.OverlayImage
import com.yapp.fitrun.core.common.convertTimeToPace
import com.yapp.fitrun.core.common.interpolateColor
import com.yapp.fitrun.core.designsystem.Body_body3_bold
import com.yapp.fitrun.core.designsystem.Body_body3_medium
import com.yapp.fitrun.core.designsystem.Body_body3_semiBold
import com.yapp.fitrun.core.designsystem.Caption_caption3_semiBold
import com.yapp.fitrun.core.designsystem.Caption_caption4_semiBold
import com.yapp.fitrun.core.designsystem.Head_h3_bold
import com.yapp.fitrun.core.designsystem.Head_h5_bold
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.feature.home.viewmodel.HomeSideEffect
import com.yapp.fitrun.feature.home.viewmodel.HomeState
import com.yapp.fitrun.feature.home.viewmodel.HomeViewModel
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun HomeRoute(
    padding: PaddingValues,
    onNavigateToRunning: () -> Unit,
    onNavigateToSetGoal: () -> Unit,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.container.stateFlow.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HomeSideEffect.ShowError -> {
                // 에러 스낵바 표시
            }

            is HomeSideEffect.ShowLocationError -> {
                // 위치 에러 표시
            }

            HomeSideEffect.NavigateToRunning -> {
                // 러닝 화면으로 이동
            }

            HomeSideEffect.NavigateToGoalSetting -> {
                // 목표 설정 화면으로 이동
            }
        }
    }

    HomeScreen(
        padding = padding,
        modifier = Modifier.fillMaxSize(),
        state = state,
        fetchCurrentLocation = viewModel::fetchCurrentLocation,
        onStartRunningClick = onNavigateToRunning,
        onSetGoalClick = onNavigateToSetGoal,
    )
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalNaverMapApi::class)
@Composable
internal fun HomeScreen(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    state: HomeState,
    fetchCurrentLocation: () -> Unit = {},
    onStartRunningClick: () -> Unit = {},
    onSetGoalClick: () -> Unit = {},
) {
    val permissionsList = mutableListOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )

    // Android 10 이상에서 백그라운드 위치 권한 추가
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        permissionsList.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        // 신체 활동 인식 권한도 추가
        permissionsList.add(Manifest.permission.ACTIVITY_RECOGNITION)
    }

    // 모든 권한을 한번에 요청
    val allPermissionsState = rememberMultiplePermissionsState(
        permissions = permissionsList,
    )

    LaunchedEffect(true) {
        if (!allPermissionsState.allPermissionsGranted) {
            allPermissionsState.launchMultiplePermissionRequest()
        }
    }

    LaunchedEffect(allPermissionsState.allPermissionsGranted) {
        if (allPermissionsState.allPermissionsGranted) {
            fetchCurrentLocation()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = padding.calculateBottomPadding())
            .background(color = Color(0xFFF9F9F9)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }

        Column(
            modifier = Modifier
                .background(
                    color = Color(0xFFF5F5F9),
                ),
        ) {
            Spacer(modifier = Modifier.padding(top = padding.calculateTopPadding()))

            Text(
                text = state.homeTitleResId?.let { stringResource(it) } ?: "",
                textAlign = TextAlign.Start,
                color = colorResource(R.color.fg_text_primary),
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 52.dp, bottom = 12.dp)
                    .fillMaxWidth(),
                style = Head_h3_bold,
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.bg_primary)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 22.dp, end = 22.dp, top = 14.dp, bottom = 18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Fire icon and text
                    Text(
                        text = "🔥",
                        style = Body_body3_bold,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "이번주 러닝 목표",
                        style = Body_body3_bold,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Edit icon
                    Icon(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = "Edit",
                        tint = colorResource(R.color.fg_nuetral_gray500),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(
                                indication = null, // Ripple 효과 없앰
                                interactionSource = remember { MutableInteractionSource() },
                            ) {
                                onSetGoalClick()
                            },
                    )
                }
                if (state.homeResult?.userGoalEntity?.weeklyRunningCount == null) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(
                                shape = RoundedCornerShape(10.dp),
                                color = colorResource(R.color.fg_nuetral_gray300),
                            ),
                    )
                } else {
                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp),
                    ) {
                        repeat(state.homeResult.userGoalEntity.weeklyRunningCount!!) { index ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 1.dp)
                                    .weight(1f)
                                    .height(4.dp)
                                    .background(
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (index < state.homeResult.recordEntity.thisWeekRunningCount) {
                                            // 그라데이션 계산: #FFE3CA -> #FF6600
                                            interpolateColor(
                                                startColor = Color(0xFFFFE3CA),
                                                endColor = Color(0xFFFF6600),
                                                fraction = index.toFloat() / (state.homeResult.recordEntity.thisWeekRunningCount - 1).coerceAtLeast(
                                                    1,
                                                ),
                                            )
                                        } else {
                                            // 회색 (나머지)
                                            colorResource(R.color.fg_nuetral_gray300)
                                        },
                                    ),
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        modifier = Modifier
                            .background(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFFFF8EE),
                            )
                            .weight(1f)
                            .padding(vertical = 11.dp, horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "목표 페이스",
                            style = Caption_caption4_semiBold,
                            color = colorResource(R.color.fg_text_tertiary),
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = convertTimeToPace(state.homeResult?.userGoalEntity?.paceGoal?.toLong()),
                            style = Caption_caption3_semiBold,
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    // Goal distance
                    Row(
                        modifier = Modifier
                            .background(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFFFF3EE),
                            )
                            .weight(1f)
                            .padding(vertical = 11.dp, horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "최근 페이스",
                            style = Caption_caption4_semiBold,
                            color = colorResource(R.color.fg_text_tertiary),
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = convertTimeToPace(state.homeResult?.recordEntity?.recentPace?.toLong()),
                            style = Caption_caption3_semiBold,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            when (allPermissionsState.allPermissionsGranted) {
                true -> {
                    MapComponent(
                        currentLocation = state.currentLocation,
                        fetchCurrentLocation = fetchCurrentLocation,
                        allPermissionsState = allPermissionsState,
                    )
                }

                false -> {
                    PermissionDeniedComponent()
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.15f),
                                Color.Black.copy(alpha = 0.1f),
                                Color.Transparent,
                            ),
                        ),
                    ),
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                shape = RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F9)),
            ) {}
            Button(
                onClick = onStartRunningClick,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .size(100.dp)
                    .clip(CircleShape)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.fg_icon_primary),
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                ),
                enabled = allPermissionsState.allPermissionsGranted,
            ) {
                Text(
                    text = "달리기",
                    fontSize = 18.sp,
                    color = colorResource(R.color.fg_text_interactive_inverse),
                    style = Head_h5_bold,
                )
            }
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalPermissionsApi::class)
@Composable
fun MapComponent(
    currentLocation: Location?,
    fetchCurrentLocation: () -> Unit,
    allPermissionsState: MultiplePermissionsState,
) {
    val cameraPositionState = rememberCameraPositionState()
    val markerState = rememberMarkerState()

    LaunchedEffect(currentLocation) {
        currentLocation?.let { location ->
            cameraPositionState.move(
                CameraUpdate.toCameraPosition(
                    CameraPosition(
                        LatLng(location.latitude, location.longitude),
                        16.0,
                    ),
                ),
            )
            markerState.position = LatLng(location.latitude, location.longitude)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                isZoomControlEnabled = false, // 줌 버튼 비활성화
                isCompassEnabled = false, // 나침반 비활성화 (기본값)
                isScaleBarEnabled = false, // 축척 바 비활성화 (기본값)
                isLocationButtonEnabled = false, // 위치 버튼 비활성화 (기본값)
                isScrollGesturesEnabled = false, // ⭐️ 드래그 비활성화
                isZoomGesturesEnabled = false, // 줌 제스처 비활성화
                isRotateGesturesEnabled = false, // 회전 제스처 비활성화
                isTiltGesturesEnabled = false,
            ),
        ) {
            Marker(
                state = markerState,
                icon = OverlayImage.fromResource(R.drawable.ic_user_location),
            )
        }
        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    bottom = 52.dp,
                    end = 20.dp,
                )
                .size(44.dp)
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.fg_border_primary),
                    shape = CircleShape,
                )
                .clip(CircleShape),
            onClick = {
                if (allPermissionsState.allPermissionsGranted) {
                    fetchCurrentLocation()
                }
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = colorResource(R.color.bg_primary),
            ),
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(R.drawable.ic_location),
                tint = Color(0xFF999999),
                contentDescription = "ic_location",
            )
        }
    }
}

@Composable
fun PermissionDeniedComponent() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "위치를 확인 불가",
            modifier = Modifier.padding(top = 16.dp),
            style = Body_body3_medium,
            color = colorResource(R.color.fg_text_secondary),
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "마이페이지의 권한 설정을 확인해 주세요.",
            style = Body_body3_semiBold,
        )
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(124.dp))
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        padding = PaddingValues(),
        state = HomeState(),
    )
}
