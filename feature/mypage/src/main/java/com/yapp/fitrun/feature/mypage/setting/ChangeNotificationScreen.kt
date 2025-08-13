package com.yapp.fitrun.feature.mypage.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.ui.FitRunTextTopAppBar
import com.yapp.fitrun.core.ui.ToggleSwitch
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageState
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun ChangeNotificationRoute(
    onBackClick: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    ChangeNotificationScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onClick = viewModel::onClickNotification,
    )
}

@Composable
internal fun ChangeNotificationScreen(
    uiState: MyPageState,
    onBackClick: () -> Unit,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_primary)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FitRunTextTopAppBar(
            title = "알림 설정",
            onLeftNavigationClick = onBackClick,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 12.dp),
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "이번주 러닝 횟수 리마인드",
                style = Body_body3_semiBold,
                textAlign = TextAlign.Start,
                color = colorResource(R.color.fg_text_primary),
            )

            Spacer(modifier = Modifier.weight(1f))

            ToggleSwitch(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .clickable { onClick() },
                checked = uiState.isNotification,
            )
        }
    }
}

@Preview
@Composable
fun ChangeNotificationScreenPreview() {
    ChangeNotificationScreen(
        uiState = MyPageState(),
        onBackClick = {},
        onClick = {},
    )
}
