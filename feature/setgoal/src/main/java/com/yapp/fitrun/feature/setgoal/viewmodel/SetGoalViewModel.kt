package com.yapp.fitrun.feature.setgoal.viewmodel

import androidx.lifecycle.ViewModel
import com.yapp.fitrun.core.domain.repository.GoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import javax.inject.Inject

@HiltViewModel
class SetGoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository
): ViewModel() {



}