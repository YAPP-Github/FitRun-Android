package com.yapp.fitrun.feature.workthrough.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class WorkThroughViewModel @Inject constructor(
): ViewModel(), ContainerHost<WorkThroughState, WorkThroughSideEffect>  {
    override val container = container<WorkThroughState, WorkThroughSideEffect>(WorkThroughState())

    init {

    }
}