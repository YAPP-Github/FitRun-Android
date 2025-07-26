package com.yapp.fitrun.feature.record.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    // TODO
) : ViewModel(), ContainerHost<RecordState, RecordSideEffect> {
    override val container = container<RecordState, RecordSideEffect>(RecordState())

    init {
        val dummyRecord = mutableListOf(
            Record(
                recordId = 1,
                startAt = "7월 22일",
                totalTime = "1:23:22",
                averagePace = "7'18''",
                totalDistance = 15.2,
                runningRouteImage = "",
            ),
            Record(
                recordId = 1,
                startAt = "7월 23일",
                totalTime = "2:12:22",
                averagePace = "6'18''",
                totalDistance = 3.2,
                runningRouteImage = "",
            ),
            Record(
                recordId = 1,
                startAt = "7월 24일",
                totalTime = "3:23:22",
                averagePace = "5'18''",
                totalDistance = 20.7,
                runningRouteImage = "",
            ),
            Record(
                recordId = 1,
                startAt = "7월 25일",
                totalTime = "1:23:22",
                averagePace = "7'18''",
                totalDistance = 15.2,
                runningRouteImage = "",
            ),
            Record(
                recordId = 1,
                startAt = "7월 26일",
                totalTime = "1:23:22",
                averagePace = "7'18''",
                totalDistance = 15.2,
                runningRouteImage = "",
            ),
            Record(
                recordId = 1,
                startAt = "7월 29일",
                totalTime = "1:23:22",
                averagePace = "7'18''",
                totalDistance = 15.2,
                runningRouteImage = "",
            ),
        )

        intent {
            reduce {
                state.copy(
                    isLoading = false,
                    totalDistance = 27.4,
                    recordCount = dummyRecord.size,
                    averagePace = "6'00''",
                    totalTime = "18:34:23",
                    timeGoalAchievedCount = 3,
                    distanceGoalAchievedCount = 4,
                    recordList = dummyRecord,
                )
            }
        }
    }
}
