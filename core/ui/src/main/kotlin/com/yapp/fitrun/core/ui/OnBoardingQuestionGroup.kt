package com.yapp.fitrun.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.yapp.fitrun.core.designsystem.Body_body4_regular
import com.yapp.fitrun.core.designsystem.Body_body4_bold
import com.yapp.fitrun.core.designsystem.Head_h2_semiBold
import com.yapp.fitrun.core.designsystem.R

@Composable
fun OnBoardingQuestionGroup(
    questionTitle: String,
    questionOptions: List<String>,
    onClick: (Int) -> Unit = {},
    visible: Boolean = true,
    density: Density = LocalDensity.current,
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf("none") }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically {
            with(density) { -50.dp.roundToPx() }
        } + expandVertically(
            expandFrom = Alignment.Top,
        ) + fadeIn(
            initialAlpha = 0f,
            animationSpec = tween(
                durationMillis = 500,
                delayMillis = 250,
                easing = FastOutSlowInEasing,
            ),
        ),
    ) {
        Column(
            modifier = Modifier.selectableGroup(),
        ) {
            Text(
                text = questionTitle,
                textAlign = TextAlign.Start,
                color = colorResource(R.color.fg_text_primary),
                style = Head_h2_semiBold,
            )

            Spacer(modifier = Modifier.height(20.dp))

            questionOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(76.dp)
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
                                onClick(questionOptions.indexOf(text))
                            },
                            role = Role.RadioButton,
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = text,
                        style = if (selectedOption == text) Body_body4_bold else Body_body4_regular,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    if (selectedOption == text) {
                        Image(
                            painter = painterResource(R.drawable.ic_onboarding_check),
                            contentDescription = "login_kakao_icon",
                            modifier = Modifier.size(24.dp),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(52.dp))
        }
    }
}

@Preview
@Composable
fun OnBoardingFirstPreview() {
    OnBoardingQuestionGroup(
        "title",
        listOf("option1", "option2", "option3"),
        onClick = {},
        visible = true,
        density = LocalDensity.current,
    )
}
