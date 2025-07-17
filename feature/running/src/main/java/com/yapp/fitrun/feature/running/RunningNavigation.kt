package com.yapp.fitrun.feature.running

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yapp.fitrun.feature.running.finish.FinishRoute
import com.yapp.fitrun.feature.running.playing.PlayingRoute
import com.yapp.fitrun.feature.running.ready.ReadyRoute
import kotlinx.serialization.Serializable

@Serializable
data object ReadyRoute // route to ForYou screen

@Serializable
data object PlayingRoute // route to ForYou screen

@Serializable
data object FinishRoute // route to ForYou screen

fun NavController.navigateToReady() {
    navigate(ReadyRoute)
}

fun NavController.navigateToPlaying() {
    navigate(PlayingRoute) {
        // 현재 화면을 백스택에서 제거
        popUpTo(currentBackStackEntry?.destination?.route ?: return@navigate) {
            inclusive = true
        }
    }
}

fun NavGraphBuilder.runningNavGraph(
    onNavigateToPlay: () -> Unit = {},
) {
    composable<ReadyRoute>(
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        }
    ) {
        ReadyRoute(
            onNavigateToPlay = onNavigateToPlay
        )
    }

    composable<PlayingRoute> {
        PlayingRoute()
    }

    composable<FinishRoute> {
        FinishRoute()
    }

}