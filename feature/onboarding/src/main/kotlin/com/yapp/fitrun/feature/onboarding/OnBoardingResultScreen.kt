package com.yapp.fitrun.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.fitrun.core.designsystem.Body_body3_medium
import com.yapp.fitrun.core.designsystem.Body_body3_semiBold
import com.yapp.fitrun.core.designsystem.Head_head1_bold
import com.yapp.fitrun.core.designsystem.InteractiveInverse
import com.yapp.fitrun.core.designsystem.InteractivePrimary
import com.yapp.fitrun.core.designsystem.InteractiveSelected
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.designsystem.TextPrimary
import com.yapp.fitrun.core.designsystem.TextSecondary
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingState
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun OnBoardingResultRoute(
    navigateToHome: () -> Unit,
    navigateToRoutine: () -> Unit,
    viewModel: OnBoardingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    OnBoardingResultScreen(
        uiState = uiState,
        navigateToHome = navigateToHome,
        navigateToRoutine = navigateToRoutine
    )
}

@Composable
internal fun OnBoardingResultScreen(
    uiState: OnBoardingState,
    navigateToHome: () -> Unit,
    navigateToRoutine: () -> Unit,
) {
    if (uiState.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp).fillMaxSize(),
            color = Color(0xFF2B1407),
            strokeWidth = 2.dp
        )
    }
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(72.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.on_boarding_result_title),
                textAlign = TextAlign.Center,
                color = TextPrimary,
                style = Head_head1_bold,
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "나는 ${uiState.runnerTypeResult} 러너에 가까워요",
                textAlign = TextAlign.Center,
                color = TextPrimary,
                style = Head_head1_bold,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.on_boarding_result_description),
                textAlign = TextAlign.Center,
                color = TextSecondary,
                style = Body_body3_medium,
            )

            Spacer(modifier = Modifier.height(100.dp))

            Image(
                modifier = Modifier
                    .width(229.dp)
                    .height(154.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.img_onboarding_result),
                contentDescription = "On Boarding Image",
                contentScale = ContentScale.Fit,
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 25.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    modifier = Modifier
                        .height(56.dp)
                        .width(163.dp),
                    enabled = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = InteractiveSelected,
                        contentColor = InteractiveSelected,
                    ),
                    onClick = navigateToHome,
                ) {
                    Text(
                        text = stringResource(R.string.on_boarding_result_go_to_home),
                        textAlign = TextAlign.Center,
                        color = InteractivePrimary,
                        style = Body_body3_semiBold,
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    modifier = Modifier
                        .height(56.dp)
                        .width(163.dp),
                    enabled = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = InteractivePrimary,
                        contentColor = InteractivePrimary,
                    ),
                    onClick = navigateToRoutine,
                ) {
                    Text(
                        text = stringResource(R.string.on_boarding_result_go_to_routine),
                        textAlign = TextAlign.Center,
                        color = InteractiveInverse,
                        style = Body_body3_semiBold,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun OnBoardingResultPreview() {
    OnBoardingResultScreen(
        uiState = OnBoardingState(isLoading = true),
        navigateToHome = {},
        navigateToRoutine = {}
    )
}
