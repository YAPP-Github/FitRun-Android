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

    fun loadRecordTest() {
        val dummy_coords = listOf(
            LatLng(37.5660645, 126.9826732),
            LatLng(37.5660294, 126.9826723),
            LatLng(37.5658526, 126.9826611),
            LatLng(37.5658040, 126.9826580),
            LatLng(37.5657697, 126.9826560),
            LatLng(37.5654413, 126.9825880),
            LatLng(37.5652157, 126.9825273),
            LatLng(37.5650560, 126.9824843),
            LatLng(37.5647789, 126.9824114),
            LatLng(37.5646788, 126.9823861),
            LatLng(37.5644062, 126.9822963),
            LatLng(37.5642519, 126.9822566),
            LatLng(37.5641517, 126.9822312),
            LatLng(37.5639965, 126.9821915),
            LatLng(37.5636536, 126.9820920),
            LatLng(37.5634424, 126.9820244),
            LatLng(37.5633241, 126.9819890),
            LatLng(37.5632772, 126.9819712),
            LatLng(37.5629404, 126.9818433),
            LatLng(37.5627733, 126.9817584),
            LatLng(37.5626694, 126.9816980),
            LatLng(37.5624588, 126.9815738),
            LatLng(37.5620376, 126.9813140),
            LatLng(37.5619426, 126.9812252),
            LatLng(37.5613227, 126.9814831),
            LatLng(37.5611995, 126.9815372),
            LatLng(37.5609414, 126.9816749),
            LatLng(37.5606785, 126.9817390),
            LatLng(37.5605659, 126.9817499),
            LatLng(37.5604892, 126.9817459),
            LatLng(37.5604540, 126.9817360),
            LatLng(37.5603484, 126.9816993),
            LatLng(37.5602092, 126.9816097),
            LatLng(37.5600048, 126.9814390),
            LatLng(37.5599702, 126.9813612),
            LatLng(37.5599401, 126.9812923),
            LatLng(37.5597114, 126.9807346),
            LatLng(37.5596905, 126.9806826),
            LatLng(37.5596467, 126.9805663),
            LatLng(37.5595203, 126.9801199),
            LatLng(37.5594901, 126.9800149),
            LatLng(37.5594544, 126.9798883),
            LatLng(37.5594186, 126.9797436),
            LatLng(37.5593948, 126.9796634),
            LatLng(37.5593132, 126.9793526),
            LatLng(37.5592831, 126.9792622),
            LatLng(37.5590904, 126.9788854),
            LatLng(37.5589081, 126.9786365),
            LatLng(37.5587088, 126.9784125),
            LatLng(37.5586699, 126.9783698),
        )

        intent {
            reduce {
                state.copy(
                    runningPoint = dummy_coords,
                )
            }
        }
    }
}
