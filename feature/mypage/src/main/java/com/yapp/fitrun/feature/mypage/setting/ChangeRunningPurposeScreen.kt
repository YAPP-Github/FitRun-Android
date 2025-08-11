package com.yapp.fitrun.feature.mypage.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.fitrun.core.designsystem.Body_body3_regular
import com.yapp.fitrun.core.designsystem.Body_body4_regular
import com.yapp.fitrun.core.designsystem.Body_body4_semiBold
import com.yapp.fitrun.core.designsystem.Head_h2_semiBold
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.ui.FitRunTextButton
import com.yapp.fitrun.core.ui.FitRunTextTopAppBar
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageState
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun ChangeRunningPurposeRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    ChangeRunningPurposeScreen(
        uiState = uiState,
        padding = padding,
        onBackClick = onBackClick,
        onClickChangeRunningPurpose = viewModel::onClickChangeRunningPurpose,
    )
}

@Composable
internal fun ChangeRunningPurposeScreen(
    uiState: MyPageState,
    padding: PaddingValues,
    onBackClick: () -> Unit,
    onClickChangeRunningPurpose: (String) -> Unit,
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(uiState.userRunningPurpose) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_primary)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FitRunTextTopAppBar(
            title = "러닝 목적 변경",
            onLeftNavigationClick = onBackClick,
        )
        Text(
            modifier = Modifier.padding(top = 80.dp),
            text = "변경하실 러닝\n목적을 선택해주세요.",
            style = Head_h2_semiBold,
            color = colorResource(R.color.fg_text_primary),
            textAlign = TextAlign.Center,
        )

        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = "이유가 분명할 수록 꾸준히 달릴 수 있어요.",
            style = Body_body3_regular,
            color = colorResource(R.color.fg_text_tertiary),
        )

        RunningPurposeGroup(
            runningPurposeOption = listOf("다이어트", "건강 관리", "체력 증진", "대회 준비"),
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected,
            iconResources = listOf(
                painterResource(R.drawable.img_fire),
                painterResource(R.drawable.img_heart),
                painterResource(R.drawable.img_battery),
                painterResource(R.drawable.img_medal),
            ),
        )

        Spacer(modifier = Modifier.weight(1f))

        FitRunTextButton(
            modifier = Modifier
                .padding(bottom = padding.calculateBottomPadding())
                .padding(start = 20.dp, end = 20.dp, bottom = 12.dp),
            onClick = { onClickChangeRunningPurpose(selectedOption) },
            text = "설정하기",
        )
    }
}

@Composable
internal fun RunningPurposeGroup(
    runningPurposeOption: List<String>,
    iconResources: List<Painter>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 44.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            FitRunRadioButton(
                modifier = Modifier.weight(0.5f),
                iconResource = iconResources[0],
                selectedOption = selectedOption,
                text = runningPurposeOption[0],
                onOptionSelected = onOptionSelected,
            )
            Spacer(modifier = Modifier.width(16.dp))

            FitRunRadioButton(
                modifier = Modifier.weight(0.5f),
                iconResource = iconResources[1],
                selectedOption = selectedOption,
                text = runningPurposeOption[1],
                onOptionSelected = onOptionSelected,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            FitRunRadioButton(
                modifier = Modifier.weight(0.5f),
                iconResource = iconResources[2],
                selectedOption = selectedOption,
                text = runningPurposeOption[2],
                onOptionSelected = onOptionSelected,
            )
            Spacer(modifier = Modifier.width(16.dp))

            FitRunRadioButton(
                modifier = Modifier.weight(0.5f),
                iconResource = iconResources[3],
                selectedOption = selectedOption,
                text = runningPurposeOption[3],
                onOptionSelected = onOptionSelected,
            )
        }
    }
}

@Composable
internal fun FitRunRadioButton(
    modifier: Modifier = Modifier,
    iconResource: Painter,
    selectedOption: String,
    text: String,
    onOptionSelected: (String) -> Unit,
) {
    Column(
        Modifier
            .height(104.dp)
            .then(modifier)
            .background(
                color = if (selectedOption == text) {
                    colorResource(R.color.bg_interactive_selected)
                } else {
                    colorResource(R.color.fg_text_interactive_inverse)
                },
                shape = RoundedCornerShape(16.dp),
            )
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(16.dp),
                color = if (selectedOption == text) {
                    colorResource(R.color.fg_text_interactive_selected)
                } else {
                    colorResource(R.color.fg_nuetral_gray400)
                },
            )
            .selectable(
                selected = (text == selectedOption),
                onClick = {
                    onOptionSelected(text)
                },
                role = Role.RadioButton,
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                },
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = iconResource,
            contentDescription = "icon",
            modifier = Modifier
                .padding(top = 24.dp)
                .width(25.dp)
                .height(30.dp),
        )

        Text(
            modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
            text = text,
            style = if (selectedOption == text) Body_body4_semiBold else Body_body4_regular,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun ChangeRunningPurposeScreenPreview() {
    ChangeRunningPurposeScreen(
        uiState = MyPageState(),
        padding = PaddingValues(),
        onBackClick = {},
        onClickChangeRunningPurpose = {},
    )
}
