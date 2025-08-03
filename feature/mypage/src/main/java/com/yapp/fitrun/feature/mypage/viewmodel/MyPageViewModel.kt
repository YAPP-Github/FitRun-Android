package com.yapp.fitrun.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor() : ViewModel(), ContainerHost<MyPageState, MyPageSideEffect> {
    override val container = container<MyPageState, MyPageSideEffect>(MyPageState())
}
