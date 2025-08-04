package com.yapp.fitrun.feature.mypage.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.fitrun.core.designsystem.Body_body3_bold
import com.yapp.fitrun.core.designsystem.Body_body3_regular
import com.yapp.fitrun.core.designsystem.Body_body3_semiBold
import com.yapp.fitrun.core.designsystem.Head_h2_bold
import com.yapp.fitrun.core.designsystem.Head_h4_bold
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.ui.FitRunTextButton
import com.yapp.fitrun.core.ui.FitRunTextTopAppBar
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageState
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ConfirmWithdrawRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    ConfirmWithdrawScreen(
        uiState = uiState,
        padding = padding,
        onBackClick = onBackClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmWithdrawScreen(
    uiState: MyPageState,
    padding: PaddingValues,
    onBackClick: () -> Unit,
) {
    var selectedReason by remember { mutableStateOf("") }
    var otherReason by remember { mutableStateOf("") }
    val withdrawalReasons = listOf(
        "앱 기능이 생각보다 별로예요",
        "자주 사용하지 않아요",
        "러닝 목표를 달성했어요",
        "건강 문제로 러닝을 포기할래요",
        "다른 앱을 사용할래요",
        "사용이 복잡하거나 불편해요",
        "기타",
    )
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_primary)),
    ) {
        if (uiState.isLoading) {
            // TODO
        }

        FitRunTextTopAppBar(
            title = "회원탈퇴",
            onLeftNavigationClick = onBackClick,
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "핏런을 정말로",
            color = colorResource(R.color.fg_text_primary),
            style = Head_h2_bold,
        )
        Row {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "회원탈퇴",
                color = colorResource(R.color.fg_text_warning),
                style = Head_h2_bold,
            )
            Text(
                modifier = Modifier,
                text = " 하시겠어요?",
                color = colorResource(R.color.fg_text_primary),
                style = Head_h2_bold,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "더 나은 서비스를 위해\n탈퇴하시는 이유를 알려주세요",
            color = colorResource(R.color.fg_text_tertiary),
            style = Body_body3_regular,
        )
        Spacer(modifier = Modifier.height(24.dp))
        withdrawalReasons.forEach { reason ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (selectedReason == reason),
                        onClick = { selectedReason = reason },
                        role = Role.RadioButton,
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = (selectedReason == reason),
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(R.color.fg_icon_interactive_primary),
                        unselectedColor = colorResource(R.color.fg_border_primary),
                    ),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = reason,
                    color = colorResource(R.color.fg_text_secondary),
                    style = Body_body3_regular,
                )
            }
        }

        // 기타 사유 입력 필드
        if (selectedReason == "기타") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            ) {
                OutlinedTextField(
                    value = otherReason,
                    onValueChange = {
                        if (it.toByteArray().size <= 100) {
                            otherReason = it
                        }
                    },
                    placeholder = {
                        Text(
                            "이유를 작성합니다 최대 100byte",
                            color = Color(0xFF999999),
                            fontSize = 14.sp,
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF999999),
                        unfocusedBorderColor = Color(0xFFDDDDDD),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = Color.Black,
                    ),
                    singleLine = false,
                    minLines = 4,
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 14.sp,
                        color = Color.Black,
                    ),
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${otherReason.toByteArray().size} / 100",
                    fontSize = 12.sp,
                    color = Color(0xFF999999),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        FitRunTextButton(
            modifier = Modifier
                .padding(bottom = padding.calculateBottomPadding())
                .padding(start = 20.dp, end = 20.dp, bottom = 12.dp),
            onClick = {
                showBottomSheet = true
            },
            enabled = selectedReason.isNotEmpty(),
            text = "다음",
            textColor = if (selectedReason.isNotEmpty()) {
                colorResource(R.color.fg_text_interactive_inverse)
            } else {
                colorResource(
                    R.color.fg_text_disabled,
                )
            },
            buttonColor = if (selectedReason.isNotEmpty()) {
                colorResource(R.color.bg_interactive_secondary_hoverd)
            } else {
                colorResource(
                    R.color.bg_disabled,
                )
            },
        )
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = bottomSheetState,
            containerColor = Color.White,
            contentColor = Color.Black,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            dragHandle = null,
        ) {
            WithdrawalConfirmBottomSheet(
                padding = padding,
                onDismiss = {
                    scope.launch {
                        bottomSheetState.hide()
                        showBottomSheet = false
                    }
                },
                onConfirm = {
                    scope.launch {
                        bottomSheetState.hide()
                        showBottomSheet = false
//                        onWithdrawClick()
                    }
                },
            )
        }
    }
}

@Composable
fun WithdrawalConfirmBottomSheet(
    padding: PaddingValues,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = padding.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 경고 아이콘
        Spacer(modifier = Modifier.height(48.dp))
        Image(painter = painterResource(R.drawable.ic_warning), contentDescription = "warning")
        Spacer(modifier = Modifier.height(17.dp))
        Text(
            text = "회원 탈퇴 전 꼭 확인해주세요!",
            textAlign = TextAlign.Center,
            style = Head_h4_bold,
            color = colorResource(R.color.fg_text_primary),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "탈퇴 시 러닝 기록, 목표 설정, 그룹 정보 등\n모든 데이터가 삭제되며,\n재가입하시더라도 복구되지 않습니다.",
            style = Body_body3_regular,
            textAlign = TextAlign.Center,
            color = colorResource(R.color.fg_text_secondary),
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "탈퇴를 진행할까요?",
            style = Body_body3_bold,
            textAlign = TextAlign.Center,
            color = colorResource(R.color.fg_text_warning),
        )
        Spacer(modifier = Modifier.height(42.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(9.dp),
        ) {
            // 취소 버튼
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Transparent,
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.dp,
                    brush = androidx.compose.ui.graphics.SolidColor(colorResource(R.color.fg_border_primary)),
                ),
            ) {
                Text(
                    text = "취소",
                    color = colorResource(R.color.fg_text_tertiary),
                    style = Body_body3_semiBold,
                )
            }

            // 탈퇴하기 버튼
            Button(
                onClick = onConfirm,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.bg_interactive_secondary_hoverd),
                ),
            ) {
                Text(
                    text = "탈퇴하기",
                    color = colorResource(R.color.fg_text_interactive_inverse),
                    style = Body_body3_semiBold,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview
@Composable
private fun ConfirmWithdrawScreenPreview() {
    ConfirmWithdrawScreen(
        uiState = MyPageState(),
        padding = PaddingValues(0.dp),
        {},
    )
}
