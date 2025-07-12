package com.yapp.fitrun.feature.home

import android.Manifest
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.yapp.fitrun.core.designsystem.Body_body3_bold
import com.yapp.fitrun.core.designsystem.Body_body3_medium
import com.yapp.fitrun.core.designsystem.Body_body3_semiBold
import com.yapp.fitrun.core.designsystem.BorderPrimary
import com.yapp.fitrun.core.designsystem.Caption_caption3_semiBold
import com.yapp.fitrun.core.designsystem.Caption_caption4_semiBold
import com.yapp.fitrun.core.designsystem.Head_head3_bold
import com.yapp.fitrun.core.designsystem.Head_head5_bold
import com.yapp.fitrun.core.designsystem.InteractiveInverse
import com.yapp.fitrun.core.designsystem.NeutralGray300
import com.yapp.fitrun.core.designsystem.NeutralGray500
import com.yapp.fitrun.core.designsystem.TextPrimary
import com.yapp.fitrun.core.designsystem.TextSecondary
import com.yapp.fitrun.core.designsystem.TextTertiary

@Composable
internal fun HomeRoute(
    padding: PaddingValues,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    HomeScreen(
        modifier = Modifier.padding(padding)
    )
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalNaverMapApi::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val permissionsList = mutableListOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    // Android 10 이상에서 백그라운드 위치 권한 추가
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        permissionsList.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        // 신체 활동 인식 권한도 추가
        permissionsList.add(Manifest.permission.ACTIVITY_RECOGNITION)
    }

    // 모든 권한을 한번에 요청
    val allPermissionsState = rememberMultiplePermissionsState(
        permissions = permissionsList
    )

    LaunchedEffect(true) {
        // 앱 시작 시 모든 권한을 한번에 요청
        if (!allPermissionsState.allPermissionsGranted) {
            allPermissionsState.launchMultiplePermissionRequest()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFF9F9F9)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.background(
                color = Color(0xFFF5F5F9)
            )
        ) {
            Spacer(modifier = Modifier.height(52.dp))
            Text(
                text = "나에게 딱 맞는 러닝을\n핏런에서 함께해요!",
                textAlign = TextAlign.Start,
                color = TextPrimary,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 12.dp)
                    .fillMaxWidth(),
                style = Head_head3_bold
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = InteractiveInverse),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 22.dp, end = 22.dp, top = 14.dp, bottom = 18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Fire icon and text
                    Text(
                        text = "🔥",
                        style = Body_body3_bold
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
                        tint = NeutralGray500,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(
                            shape = RoundedCornerShape(10.dp),
                            color = NeutralGray300
                        ),
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .background(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFFFF8EE)
                            )
                            .weight(1f)
                            .padding(vertical = 11.dp, horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "목표 페이스",
                            style = Caption_caption4_semiBold,
                            color = TextTertiary
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "-'--\"",
                            style = Caption_caption3_semiBold
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    // Goal distance
                    Row(
                        modifier = Modifier
                            .background(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFFFF3EE)
                            )
                            .weight(1f)
                            .padding(vertical = 11.dp, horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Text(
                            text = "최근 페이스",
                            style = Caption_caption4_semiBold,
                            color = TextTertiary
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "-'--\"",
                            style = Caption_caption3_semiBold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (allPermissionsState.allPermissionsGranted) {
                true -> {
                    MapComponent()
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
                            )
                        )
                    )
            )
            Card(
                modifier = Modifier.fillMaxWidth().height(12.dp),
                shape = RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F9)),
            ){}
            Button(
                onClick = { /* Handle click */ },
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .size(100.dp)
                    .clip(CircleShape)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Text(
                    text = "달리기",
                    fontSize = 18.sp,
                    color = Color.White,
                    style = Head_head5_bold
                )
            }
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapComponent() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            uiSettings = MapUiSettings(
                isZoomControlEnabled = false,  // 줌 버튼 비활성화
                isCompassEnabled = false,        // 나침반 비활성화 (기본값)
                isScaleBarEnabled = false,       // 축척 바 비활성화 (기본값)
                isLocationButtonEnabled = false  // 위치 버튼 비활성화 (기본값)
            )
        )
        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    bottom = 52.dp,
                    end = 20.dp
                )
                .size(44.dp)
                .border(
                    width = 1.dp,
                    color = BorderPrimary,
                    shape = CircleShape
                )
                .clip(CircleShape),
            onClick = {},
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = InteractiveInverse,
            )
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(R.drawable.ic_location),
                tint = Color(0xFF999999),
                contentDescription = "ic_location"
            )
        }
    }
}

@Composable
fun PermissionDeniedComponent() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "위치를 확인할 수 없습니다",
            modifier = Modifier.padding(top = 16.dp),
            style = Body_body3_medium,
            color = TextSecondary
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "마이페이지의 권한 설정을 확인해 주세요!",
            style = Body_body3_semiBold
        )
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(124.dp))
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
