package com.yapp.fitrun.feature.record.viewmodel

sealed interface RecordSideEffect {
    data class OnNavigateToRecordDetail(val recordId: Int) : RecordSideEffect
}
