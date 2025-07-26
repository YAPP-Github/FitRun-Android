package com.yapp.fitrun.feature.record.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RecordDetailViewModel @Inject constructor(
    // TODO: RecordRepository 추가 예정
) : ViewModel(), ContainerHost<RecordDetailState, RecordDetailSideEffect> {
    override val container = container<RecordDetailState, RecordDetailSideEffect>(RecordDetailState())

    fun loadRecordDetail(recordId: Int) = intent {
        reduce { state.copy(isLoading = true) }

        // 현재는 더미 데이터 사용
        val dummyRecord = getDummyRecord(recordId)

        reduce {
            state.copy(
                isLoading = false,
                record = dummyRecord,
            )
        }
    }

    private fun getDummyRecord(recordId: Int): Record {
        // RecordViewModel과 동일한 더미 데이터 사용
        val dummyRecords = listOf(
            Record(
                recordId = 1,
                startAt = "7월 22일",
                totalTime = "1:23:22",
                averagePace = "7'18''",
                totalDistance = 15.2,
                runningRouteImage = "",
            ),
            Record(
                recordId = 2,
                startAt = "7월 23일",
                totalTime = "2:12:22",
                averagePace = "6'18''",
                totalDistance = 3.2,
                runningRouteImage = "",
            ),
            Record(
                recordId = 3,
                startAt = "7월 24일",
                totalTime = "3:23:22",
                averagePace = "5'18''",
                totalDistance = 20.7,
                runningRouteImage = "",
            ),
            Record(
                recordId = 4,
                startAt = "7월 25일",
                totalTime = "1:23:22",
                averagePace = "7'18''",
                totalDistance = 15.2,
                runningRouteImage = "",
            ),
            Record(
                recordId = 5,
                startAt = "7월 26일",
                totalTime = "1:23:22",
                averagePace = "7'18''",
                totalDistance = 15.2,
                runningRouteImage = "",
            ),
            Record(
                recordId = 6,
                startAt = "7월 29일",
                totalTime = "1:23:22",
                averagePace = "7'18''",
                totalDistance = 15.2,
                runningRouteImage = "",
            ),
        )

        return dummyRecords.find { it.recordId == recordId } ?: dummyRecords.first()
    }
}
