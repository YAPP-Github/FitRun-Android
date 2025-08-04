package com.yapp.fitrun.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.fitrun.core.designsystem.Body_body3_semiBold
import com.yapp.fitrun.core.designsystem.Body_body4_semiBold
import com.yapp.fitrun.core.designsystem.Caption_caption1_medium
import com.yapp.fitrun.core.designsystem.Caption_caption2_medium
import com.yapp.fitrun.core.designsystem.Head_h4_bold
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.designsystem.pretendardFamily
import com.yapp.fitrun.core.ui.noRippleClickable
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageState
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun MyPageRoute(
    padding: PaddingValues,
    onNavigateToChangeRunningLevel: () -> Unit,
    onNavigateToChangeRunningPurpose: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserInfo()
    }

    MyPageScreen(
        uiState = uiState,
        padding = padding,
        onNavigateToChangeRunningLevel = onNavigateToChangeRunningLevel,
        onNavigateToChangeRunningPurpose = onNavigateToChangeRunningPurpose,
    )
}

@Composable
internal fun MyPageScreen(
    uiState: MyPageState,
    padding: PaddingValues,
    onNavigateToChangeRunningLevel: () -> Unit,
    onNavigateToChangeRunningPurpose: () -> Unit,
) {
    if (uiState.isLoading) {
        // TODO
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_secondary)),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = padding.calculateTopPadding(),
            bottom = padding.calculateBottomPadding(),
        ),
    ) {
        // 상단 타이틀
        item {
            Text(
                text = stringResource(R.string.my_page_title),
                style = Head_h4_bold,
                color = colorResource(R.color.fg_text_primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            )
        }

        // 유저 정보 섹션
        item {
            UserInfoSection(
                uiState = uiState,
                onNavigateToChangeRunningLevel = onNavigateToChangeRunningLevel,
                onNavigateToChangeRunningPurpose = onNavigateToChangeRunningPurpose,
            )
        }

        // 러닝 목표 섹션
        item {
            RunningGoalSection(
                uiState = uiState,
            )
        }

        // 설정 섹션
        item {
            SettingSection()
        }

        // 서비스 섹션
        item {
            ServiceSection()
        }

        item {
            FooterSection()
        }
    }
}

@Composable
internal fun UserInfoSection(
    uiState: MyPageState,
    onNavigateToChangeRunningLevel: () -> Unit,
    onNavigateToChangeRunningPurpose: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .wrapContentHeight()
            .background(
                colorResource(R.color.bg_interactive_secondary),
                shape = RoundedCornerShape(dimensionResource(R.dimen.border_radius_600)),
            ),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 20.dp),
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = uiState.userNickName,
                        style = Body_body3_semiBold,
                        color = colorResource(R.color.fg_text_interactive_inverse),
                    )

                    Image(
                        modifier = Modifier
                            .size(22.dp)
                            .padding(start = 4.dp),
                        painter = painterResource(R.drawable.ic_mypage_kakao_logo),
                        contentDescription = "kakao account",
                        contentScale = ContentScale.Fit,
                    )
                }

                Text(
                    text = uiState.userEmail,
                    style = Caption_caption2_medium,
                    color = colorResource(R.color.fg_text_tertiary),
                    modifier = Modifier.padding(top = 4.dp),
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {},
            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(start = 4.dp),
                    painter = painterResource(R.drawable.ic_arrow_next),
                    contentDescription = "kakao account",
                    contentScale = ContentScale.Fit,
                )
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colorResource(R.color.gray_800)),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 20.dp)
                .noRippleClickable { },
        ) {
            UserDataComponent(
                modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                onClick = onNavigateToChangeRunningLevel,
                iconResource = painterResource(R.drawable.img_chicken),
                title = stringResource(R.string.my_page_user_level),
                content = uiState.userLevel,
            )

            UserDataComponent(
                onClick = onNavigateToChangeRunningPurpose,
                iconResource = painterResource(R.drawable.img_fire),
                title = stringResource(R.string.my_page_user_goal),
                content = uiState.userRunningPurpose,
            )
        }
    }
}

@Composable
internal fun RunningGoalSection(
    uiState: MyPageState,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 28.dp, bottom = 44.dp)
            .wrapContentHeight(),
    ) {
        Text(
            text = stringResource(R.string.my_page_user_goal),
            style = Body_body4_semiBold,
            color = colorResource(R.color.fg_text_secondary),
        )

        Column(
            modifier = Modifier
                .padding(top = 12.dp)
                .background(
                    colorResource(R.color.bg_primary),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.border_radius_600)),
                ),
        ) {
            Spacer(Modifier.height(16.dp))

            SettingGoalComponent(
                title = stringResource(R.string.my_page_user_goal_distance),
                content = uiState.userGoalDistance,
                iconResource = painterResource(R.drawable.ic_track),
                iconSize = DpSize(width = 31.dp, height = 21.dp),
            )

            Spacer(Modifier.height(4.dp))

            SettingGoalComponent(
                title = stringResource(R.string.my_page_user_goal_time),
                content = uiState.userGoalTime,
                iconResource = painterResource(R.drawable.ic_clock),
                iconSize = DpSize(width = 20.dp, height = 21.dp),
            )

            Spacer(Modifier.height(4.dp))

            SettingGoalComponent(
                title = stringResource(R.string.my_page_user_goal_pace),
                content = uiState.userGoalPace,
                iconResource = painterResource(R.drawable.ic_target),
                iconSize = DpSize(width = 24.dp, height = 24.dp),
            )

            Spacer(Modifier.height(4.dp))

            SettingGoalComponent(
                title = stringResource(R.string.my_page_user_goal_frequency),
                content = uiState.userGoalFrequency,
                iconResource = painterResource(R.drawable.ic_frequency),
                iconSize = DpSize(width = 28.dp, height = 28.dp),
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
internal fun SettingSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 44.dp)
            .wrapContentHeight(),
    ) {
        Text(
            text = stringResource(R.string.my_page_setting),
            style = Body_body4_semiBold,
            color = colorResource(R.color.fg_text_secondary),
        )

        Column(
            modifier = Modifier
                .padding(top = 12.dp)
                .background(
                    colorResource(R.color.bg_primary),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.border_radius_600)),
                ),
        ) {
            Spacer(Modifier.height(12.dp))

            SettingTextComponent(title = stringResource(R.string.my_page_setting_running))
            Spacer(Modifier.height(4.dp))
            SettingTextComponent(title = stringResource(R.string.my_page_setting_notification))
            Spacer(Modifier.height(4.dp))
            SettingTextComponent(title = stringResource(R.string.my_page_setting_permission))

            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
internal fun ServiceSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 28.dp)
            .wrapContentHeight(),
    ) {
        Text(
            text = stringResource(R.string.my_page_service),
            style = Body_body4_semiBold,
            color = colorResource(R.color.fg_text_secondary),
        )

        Column(
            modifier = Modifier
                .padding(top = 12.dp)
                .background(
                    colorResource(R.color.bg_primary),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.border_radius_600)),
                ),
        ) {
            Spacer(Modifier.height(12.dp))

            SettingTextComponent(title = stringResource(R.string.my_page_service_policy))
            Spacer(Modifier.height(4.dp))
            SettingTextComponent(title = stringResource(R.string.my_page_service_usage))
            Spacer(Modifier.height(4.dp))
            AppVersionComponent(versionInfo = "1.0.0")

            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
internal fun FooterSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(186.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(top = 23.dp),
            text = stringResource(R.string.app_name),
            style = TextStyle(
                fontFamily = pretendardFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 27.sp,
            ),
            color = colorResource(R.color.gray_600),
        )

        Text(
            modifier = Modifier.padding(top = 18.dp),
            text = "sample@gmail.com",
            style = Caption_caption2_medium,
            color = colorResource(R.color.gray_700),
        )
    }
}

@Composable
internal fun UserDataComponent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    iconResource: Painter,
    title: String,
    content: String,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .noRippleClickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(30.dp),
            painter = iconResource,
            contentDescription = "icon",
            contentScale = ContentScale.Fit,
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(
                text = title,
                style = Caption_caption2_medium,
                color = colorResource(R.color.fg_text_tertiary),
            )

            Text(
                text = content,
                style = Body_body4_semiBold,
                color = colorResource(R.color.fg_text_interactive_inverse),
                modifier = Modifier.padding(top = 2.dp),
            )
        }
    }
}

@Composable
internal fun SettingGoalComponent(
    title: String,
    content: String,
    iconResource: Painter,
    iconSize: DpSize,
) {
    BaseSettingComponent {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        colorResource(R.color.bg_secondary),
                        shape = RoundedCornerShape(dimensionResource(R.dimen.border_radius_500)),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(iconSize),
                    painter = iconResource,
                    contentDescription = "icon",
                    contentScale = ContentScale.Fit,
                )
            }

            Column(
                modifier = Modifier.padding(start = 12.dp),
            ) {
                Text(
                    text = title,
                    style = Caption_caption2_medium,
                    color = colorResource(R.color.fg_text_tertiary),
                )

                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = content,
                    style = Body_body3_semiBold,
                    color = colorResource(R.color.fg_text_primary),
                )
            }
        }
    }
}

@Composable
internal fun SettingTextComponent(
    title: String,
) {
    BaseSettingComponent {
        Text(
            text = title,
            style = Body_body3_semiBold,
            color = colorResource(R.color.fg_text_primary),
        )
    }
}

@Composable
internal fun AppVersionComponent(
    versionInfo: String,
) {
    BaseSettingComponent(
        isInfoText = true,
        infoText = versionInfo,
    ) {
        Text(
            text = stringResource(R.string.my_page_service_app_version),
            style = Body_body3_semiBold,
            color = colorResource(R.color.fg_text_primary),
        )
    }
}

@Composable
internal fun BaseSettingComponent(
    modifier: Modifier = Modifier,
    isInfoText: Boolean = false,
    infoText: String = "",
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .padding(vertical = 4.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()

        Spacer(modifier = Modifier.weight(1f))

        if (isInfoText) {
            Text(
                text = infoText,
                style = Caption_caption1_medium,
                color = colorResource(R.color.fg_text_tertiary),
            )
        } else {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .noRippleClickable { },
            ) {
                Image(
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterEnd)
                        .padding(start = 4.dp),
                    painter = painterResource(R.drawable.ic_arrow_next),
                    contentDescription = "kakao account",
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.CenterEnd,
                    colorFilter = ColorFilter.tint(
                        colorResource(R.color.fg_icon_secondary),
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
fun MyPageScreenPreview() {
    MyPageScreen(
        uiState = MyPageState(),
        padding = PaddingValues(),
        onNavigateToChangeRunningLevel = {},
        onNavigateToChangeRunningPurpose = {},
    )
}

@Preview
@Composable
fun SettingTextComponentPreview() {
    SettingTextComponent(
        title = "러닝 설정",
    )
}

@Preview
@Composable
fun SettingGoalComponentPreview() {
    SettingGoalComponent(
        title = "목표 거리",
        content = "5km",
        iconResource = painterResource(R.drawable.ic_clock),
        iconSize = DpSize(width = 20.dp, height = 21.dp),
    )
}
