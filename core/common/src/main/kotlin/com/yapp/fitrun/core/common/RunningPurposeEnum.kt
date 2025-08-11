package com.yapp.fitrun.core.common

enum class RunningPurpose(
    val purpose: String,
) {
    A("WEIGHT_LOSS_PURPOSE"),
    B("HEALTH_MAINTENANCE_PURPOSE"),
    C("DAILY_STRENGTH_IMPROVEMENT"),
    D("COMPETITION_PREPARATION"),
}

fun convertRunningPurpose(purpose: String): String {
    if (purpose == RunningPurpose.A.purpose)
        return "다이어트"
    else if (purpose == RunningPurpose.B.purpose)
        return "건강 관리"
    else if (purpose == RunningPurpose.C.purpose)
        return "체력 증진"
    else if (purpose == RunningPurpose.D.purpose)
        return "대회 준비"
    else
        return ""
}
