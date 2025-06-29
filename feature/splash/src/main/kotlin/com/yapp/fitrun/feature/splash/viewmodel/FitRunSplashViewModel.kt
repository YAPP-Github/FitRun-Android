package com.yapp.fitrun.feature.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FitRunSplashViewModel @Inject constructor(
    // TODO: auto login repository
) : ViewModel(), ContainerHost<FitRunSplashState, FitRunSplashSideEffect> {

    override val container = container<FitRunSplashState, FitRunSplashSideEffect>(FitRunSplashState())

    init {
        // TODO: token validate logic

        // 임시로 login fail 설정해둠
        intent {
            postSideEffect(FitRunSplashSideEffect.AutoLoginFail)
        }
    }

    fun autoLoginFail() = intent {
        viewModelScope.launch {
            postSideEffect(FitRunSplashSideEffect.AutoLoginFail)
        }
    }

    fun autoLoginSuccess() = intent {
        postSideEffect(FitRunSplashSideEffect.AutoLoginSuccess)
    }

}