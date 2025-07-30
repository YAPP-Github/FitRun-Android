package com.yapp.fitrun.feature.record.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.naver.maps.geometry.LatLng
import com.yapp.fitrun.core.common.convertTimeToPace
import com.yapp.fitrun.core.common.formatMillisToString
import com.yapp.fitrun.core.domain.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RecordDetailViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
) : ViewModel(), ContainerHost<RecordDetailState, RecordDetailSideEffect> {
    override val container =
        container<RecordDetailState, RecordDetailSideEffect>(RecordDetailState())

    fun loadRecordDetail(recordId: Int) = intent {
        reduce { state.copy(isLoading = true) }
        recordRepository.getRecordDetail(recordId)
            .onSuccess { response ->
                reduce {
                    state.copy(
                        isLoading = false,
                        title = response.title,
                        recordId = response.recordId,
                        startAt = response.startAt,
                        totalTime = formatMillisToString(response.totalTime),
                        averagePace = convertTimeToPace(response.averagePace),
                        totalDistance = response.totalDistance,
                        runningPoint = response.runningPoints.map {
                            LatLng(it.lat, it.lon)
                        },
                        segments = response.segments.map {
                            RecordDetailInfo(
                                orderNo = it.orderNo,
                                distanceMeter = it.distanceMeter,
                                averagePace = convertTimeToPace(it.averagePace),
                            )
                        },
                    )
                }
            }
            .onFailure {
                reduce { state.copy(isLoading = false) }
                Log.e(
                    this@RecordDetailViewModel.javaClass.name,
                    "getRecordDetail: " + it.message.toString(),
                )
            }
    }
}
