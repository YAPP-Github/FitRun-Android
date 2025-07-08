package com.yapp.fitrun.core.ui

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.fitrun.core.designsystem.Body_body4_Regular
import com.yapp.fitrun.core.designsystem.Body_body4_bold
import com.yapp.fitrun.core.designsystem.Head_head2_semiBold
import com.yapp.fitrun.core.designsystem.InteractivePrimary
import com.yapp.fitrun.core.designsystem.InteractiveSelected
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.designsystem.TextPrimary
import com.yapp.fitrun.core.designsystem.base_white
import com.yapp.fitrun.core.designsystem.gray_400

@Composable
fun OnBoardingQuestionGroup(
    questionTitle: String,
    questionOptions: List<String>,
    onClick: () -> Unit = {},
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(questionOptions[0]) }

    Column(
        modifier = Modifier.selectableGroup(),
    ) {
        Text(
            text = questionTitle,
            textAlign = TextAlign.Start,
            color = TextPrimary,
            style = Head_head2_semiBold,
        )

        Spacer(modifier = Modifier.height(20.dp))

        questionOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(76.dp)
                    .background(if (selectedOption == text) InteractiveSelected else base_white)
                    .border(
                        width = 2.dp,
                        color = if (selectedOption == text) InteractivePrimary else gray_400,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            onClick()
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    style = if (selectedOption == text) Body_body4_bold else Body_body4_Regular,
                )

                Spacer(modifier = Modifier.weight(1f))

                if (selectedOption == text) {
                    Image(
                        painter = painterResource(R.drawable.ic_onboarding_check),
                        contentDescription = "login_kakao_icon",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(52.dp))
    }
}

@Preview
@Composable
fun OnBoardingFirstPreview() {
    OnBoardingQuestionGroup(
        "title",
        listOf("option1", "option2", "option3")
    )
}
