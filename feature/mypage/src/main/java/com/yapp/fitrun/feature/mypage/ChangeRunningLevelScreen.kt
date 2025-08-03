package com.yapp.fitrun.feature.mypage

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
internal fun ChangeRunningLevelRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    ChangeRunningLevelScreen(
        uiState = uiState,
        padding = padding,
        onBackClick = onBackClick,
    )
}

@Composable
internal fun ChangeRunningLevelScreen(
    uiState: MyPageState,
    padding: PaddingValues,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_primary)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (uiState.isLoading) {
            // TODO
        }

        FitRunTextTopAppBar(
            title = "러닝 레벨 변경",
            onLeftNavigationClick = onBackClick,
        )
        Text(
            modifier = Modifier.padding(top = 80.dp),
            text = "변경하실 러닝\n레벨을 선택해주세요.",
            style = Head_h2_semiBold,
            color = colorResource(R.color.fg_text_primary),
            textAlign = TextAlign.Center,
        )

        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = "지금 나의 체력에 맞는 레벨로 다시 설정해보세요.",
            style = Body_body3_regular,
            color = colorResource(R.color.fg_text_tertiary),
        )

        RunningLevelGroup(
            userLevelOption = listOf("가볍게 달리는\n워밍업 러너", "꾸준히 달리는\n루틴 러너", "성장 중인\n챌린지 러너"),
            selected = "가볍게 달리는\n워밍업 러너",
            iconResource = listOf(
                painterResource(R.drawable.img_chicken),
                painterResource(R.drawable.img_stopwatch),
                painterResource(R.drawable.img_rocket),
            ),
            onClick = {},
        )

        Spacer(modifier = Modifier.weight(1f))

        FitRunTextButton(
            modifier = Modifier
                .padding(bottom = padding.calculateBottomPadding())
                .padding(start = 20.dp, end = 20.dp, bottom = 12.dp),
            onClick = { },
            text = "설정하기",
        )
    }
}

@Composable
internal fun RunningLevelGroup(
    userLevelOption: List<String>,
    selected: String,
    iconResource: List<Painter>,
    onClick: (Int) -> Unit = {},
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(selected) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 44.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
    ) {
        userLevelOption.forEachIndexed { index, text ->
            Column(
                Modifier
                    .height(124.dp)
                    .width(101.dp)
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
                            onClick(userLevelOption.indexOf(text))
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
                    painter = iconResource[index],
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
    }
}

@Preview
@Composable
fun ChangeRunningLevelScreenPreview() {
    ChangeRunningLevelScreen(
        uiState = MyPageState(),
        padding = PaddingValues(),
        onBackClick = {},
    )
}
