package com.yapp.fitrun.feature.mypage.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.fitrun.core.designsystem.Body_body3_semiBold
import com.yapp.fitrun.core.designsystem.Caption_caption2_medium
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.ui.FitRunTextTopAppBar
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageState
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun ProfileRoute(
    viewModel: MyPageViewModel = hiltViewModel(),
    padding: PaddingValues,
    onWithdrawClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.collectAsState()
    padding
    ProfileScreen(
        uiState = uiState,
        onWithdrawClick = onWithdrawClick,
        onBackClick = onBackClick,
    )
}

@Composable
internal fun ProfileScreen(
    uiState: MyPageState,
    onWithdrawClick: () -> Unit,
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
            title = "프로필",
            onLeftNavigationClick = onBackClick,
        )

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
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
                Spacer(modifier = Modifier.weight(1f))
            }

            Text(
                text = uiState.userEmail,
                style = Caption_caption2_medium,
                color = colorResource(R.color.fg_text_tertiary),
                modifier = Modifier.padding(top = 4.dp),
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(colorResource(R.color.bg_secondary)),
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(horizontal = 20.dp, vertical = 16.dp),
            style = Body_body3_semiBold,
            text = "로그아웃",
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onWithdrawClick() }
                .padding(horizontal = 20.dp, vertical = 16.dp),
            style = Body_body3_semiBold,
            text = "회원탈퇴",
        )
        Spacer(modifier = Modifier.height(12.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(colorResource(R.color.bg_secondary)),
        )
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(
        uiState = MyPageState(),
        {},
        {},
    )
}
