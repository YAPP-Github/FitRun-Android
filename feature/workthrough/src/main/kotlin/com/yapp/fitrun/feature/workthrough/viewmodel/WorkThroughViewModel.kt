package com.yapp.fitrun.feature.workthrough.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import com.yapp.fitrun.core.designsystem.R
import org.orbitmvi.orbit.syntax.simple.reduce

@HiltViewModel
class WorkThroughViewModel @Inject constructor(
) : ViewModel(), ContainerHost<WorkThroughState, WorkThroughSideEffect> {
    override val container = container<WorkThroughState, WorkThroughSideEffect>(WorkThroughState())

    private val titleTextList: List<Int> = listOf(
        R.string.work_through_1_title,
        R.string.work_through_2_title,
        R.string.work_through_3_title
    )
    private val descriptionTextList: List<Int> = listOf(
        R.string.work_through_1_description,
        R.string.work_through_2_description,
        R.string.work_through_3_description
    )

    init {
        intent {
            reduce {
                state.copy(
                    titleTextList = titleTextList,
                    descriptionTextList = descriptionTextList,
                )
            }
        }
    }

    fun onStartClick() = intent {
        postSideEffect(WorkThroughSideEffect.OnClickStartButton)
    }
}