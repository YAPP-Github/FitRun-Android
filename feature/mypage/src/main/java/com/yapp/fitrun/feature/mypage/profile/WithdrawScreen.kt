package com.yapp.fitrun.feature.mypage.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.fitrun.core.designsystem.Body_body3_regular
import com.yapp.fitrun.core.designsystem.Caption_caption2_semiBold
import com.yapp.fitrun.core.designsystem.Head_h2_bold
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.ui.FitRunTextButton
import com.yapp.fitrun.core.ui.FitRunTextTopAppBar
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageState
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun WithdrawRoute(
    padding: PaddingValues,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    WithdrawScreen(
        uiState = uiState,
        padding = padding,
        onNextClick = onNextClick,
        onBackClick = onBackClick,
    )
}

@Composable
internal fun WithdrawScreen(
    uiState: MyPageState,
    padding: PaddingValues,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    var agree by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_primary)),
    ) {
        if (uiState.isLoading) {
            // TODO
        }

        FitRunTextTopAppBar(
            title = "탈퇴 약관동의",
            onLeftNavigationClick = onBackClick,
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "회원탈퇴 전",
            color = colorResource(R.color.fg_text_warning),
            style = Head_h2_bold,
        )
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "꼭 확인해 주세요!",
            color = colorResource(R.color.fg_text_primary),
            style = Head_h2_bold,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "탈퇴 시 회원님의 러닝 기록, 목표 설정, 크루 정보 등 모든 데이터가 삭제됩니다. 신중히 결정해 주세요.",
            color = colorResource(R.color.fg_text_tertiary),
            style = Body_body3_regular,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = agree,
                    onClick = { agree = !agree },
                    role = Role.RadioButton,
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                selected = agree,
                onClick = null,
                colors = RadioButtonDefaults.colors(
                    selectedColor = colorResource(R.color.fg_icon_interactive_primary),
                    unselectedColor = colorResource(R.color.fg_border_primary),
                ),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "회원탈퇴 약관 동의",
                color = colorResource(R.color.fg_text_secondary),
                style = Body_body3_regular,
            )
            Text(
                text = " *",
                color = colorResource(R.color.fg_text_warning),
                style = Body_body3_regular,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "약관 보기",
                style = Caption_caption2_semiBold,
                color = colorResource(R.color.fg_text_interactive_secondary),
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        FitRunTextButton(
            modifier = Modifier
                .padding(bottom = padding.calculateBottomPadding())
                .padding(start = 20.dp, end = 20.dp, bottom = 12.dp),
            onClick = {
                onNextClick()
            },
            enabled = agree,
            text = "다음",
            textColor = if (agree) {
                colorResource(R.color.fg_text_interactive_inverse)
            } else {
                colorResource(
                    R.color.fg_text_disabled,
                )
            },
            buttonColor = if (agree) {
                colorResource(R.color.bg_interactive_secondary_hoverd)
            } else {
                colorResource(
                    R.color.bg_disabled,
                )
            },
        )
    }
}

@Preview
@Composable
private fun WithdrawScreenPreview() {
    WithdrawScreen(
        uiState = MyPageState(),
        padding = PaddingValues(0.dp),
        {},
        {},
    )
}
