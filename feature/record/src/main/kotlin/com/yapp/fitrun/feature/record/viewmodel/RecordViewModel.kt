package com.yapp.fitrun.feature.record.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.fitrun.core.domain.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
) : ViewModel(), ContainerHost<RecordState, RecordSideEffect> {
    override val container = container<RecordState, RecordSideEffect>(RecordState())

    init {
        viewModelScope.launch {
            recordRepository.getRecordList()
                .onSuccess { response ->
                    intent {
                        reduce {
                            state.copy(
                                isLoading = false,
                                totalDistance = response.totalDistance / 1000f,
                                recordCount = response.recordCount,
                                averagePace = response.averagePace.toString(), // TODO: calculate pace
                                totalTime = response.totalTime.toString(), // TODO: calculate time
                                timeGoalAchievedCount = response.timeGoalAchievedCount,
                                distanceGoalAchievedCount = response.distanceGoalAchievedCount,
                                recordList = response.records.map {
                                    RecordInfo(
                                        recordId = it.recordId,
                                        startAt = it.startAt,
                                        totalTime = it.totalTime.toString(), // TODO: calculate pace
                                        averagePace = it.averagePace.toString(), // TODO: calculate time
                                        totalDistance = it.totalDistance,
                                        runningRouteImage = it.imageUrl,
                                    )
                                },
                            )
                        }
                    }
                }
                .onFailure {
                    Log.e(
                        this@RecordViewModel.javaClass.name,
                        "getRecordList: " + it.message.toString(),
                    )
                }
        }
    }
}
