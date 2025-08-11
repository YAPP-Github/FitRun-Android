package com.yapp.fitrun.feature.mypage.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.fitrun.core.designsystem.Body_body3_semiBold
import com.yapp.fitrun.core.designsystem.Caption_caption2_medium
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.ui.FitRunTextTopAppBar
import com.yapp.fitrun.core.ui.ToggleSwitch
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageState
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun ChangeRunningSettingRoute(
    onBackClick: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    ChangeRunningSettingScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onAudioCoachingClick = viewModel::onClickAudioCoaching,
        onAudioFeedbackClick = viewModel::onClickAudioFeedback,
    )
}

@Composable
internal fun ChangeRunningSettingScreen(
    uiState: MyPageState,
    onBackClick: () -> Unit,
    onAudioCoachingClick: () -> Unit,
    onAudioFeedbackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_primary)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FitRunTextTopAppBar(
            title = "러닝 설정",
            onLeftNavigationClick = onBackClick,
        )

        Spacer(modifier = Modifier.height(12.dp))

        RunningSettingToggleSwitch(
            title = "오디오 코칭",
            description = "러닝 중 올바른 자세에 대한 안내가 음성으로 제공되는 기능이에요",
            onClick = onAudioCoachingClick,
            isChecked = uiState.isAudioCoaching,
        )

        Spacer(modifier = Modifier.height(16.dp))

        RunningSettingToggleSwitch(
            title = "오디오 피드백",
            description = "러닝 중 실시간 페이스에 따라 맞춤형 안내가 음성으로 제공되는 기능이에요",
            onClick = onAudioFeedbackClick,
            isChecked = uiState.isAudioFeedback,
        )
    }
}

@Composable
internal fun RunningSettingToggleSwitch(
    title: String,
    description: String,
    onClick: () -> Unit = {},
    isChecked: Boolean,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
    ) {
        Column {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = title,
                style = Body_body3_semiBold,
                textAlign = TextAlign.Start,
                color = colorResource(R.color.fg_text_primary),
            )

            Text(
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 4.dp)
                    .width(257.dp),
                text = description,
                style = Caption_caption2_medium,
                textAlign = TextAlign.Start,
                color = colorResource(R.color.fg_text_tertiary),
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        ToggleSwitch(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .clickable {
                    onClick()
                },
            checked = isChecked,
        )
    }
}

@Preview
@Composable
fun ChangeRunningSettingScreenPreview() {
    ChangeRunningSettingScreen(
        uiState = MyPageState(),
        onBackClick = {},
        onAudioCoachingClick = {},
        onAudioFeedbackClick = {},
    )
}
