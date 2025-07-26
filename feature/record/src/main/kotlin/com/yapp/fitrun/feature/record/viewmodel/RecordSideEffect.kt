package com.yapp.fitrun.feature.record.viewmodel

sealed interface RecordSideEffect {
    data object OnNavigateToRecordDetail : RecordSideEffect
}
