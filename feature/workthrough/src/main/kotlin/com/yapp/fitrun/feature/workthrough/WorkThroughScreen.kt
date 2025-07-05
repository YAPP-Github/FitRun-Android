package com.yapp.fitrun.feature.workthrough

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.fitrun.feature.workthrough.viewmodel.WorkThroughViewModel


@Composable
internal fun WorkThroughRoute(
    navigateToLogin: () -> Unit,
    viewModel: WorkThroughViewModel = hiltViewModel(),
) {
    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { sideEffect ->
        }
    }
    WorkThroughScreen()
}

@Composable
internal fun WorkThroughScreen() {
}

@Preview
@Composable
fun WorkThroughScreenPreview() {
    WorkThroughScreen()
}
