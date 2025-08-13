package com.yapp.fitrun.feature.mypage.service

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.fitrun.core.designsystem.Body_body3_regular
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.ui.FitRunTextTopAppBar

@Composable
internal fun TermsAndConditionsRoute(
    onBackClick: () -> Unit,
) {
    TermsAndConditionsScreen(
        onBackClick = onBackClick,
    )
}

@Composable
internal fun TermsAndConditionsScreen(
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(colorResource(R.color.bg_primary)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FitRunTextTopAppBar(
            title = "약관 관리",
            onLeftNavigationClick = onBackClick,
        )

        Text(
            modifier = Modifier
                .padding(top = 12.dp, bottom = 30.dp)
                .padding(horizontal = 20.dp)
                .navigationBarsPadding(),
            text = stringResource(R.string.my_page_terms_and_conditions),
            style = Body_body3_regular,
            textAlign = TextAlign.Start,
            color = colorResource(R.color.fg_text_secondary),
        )
    }
}

@Preview
@Composable
fun TermsAndConditionsScreenPreview() {
    TermsAndConditionsScreen(
        onBackClick = {},
    )
}
