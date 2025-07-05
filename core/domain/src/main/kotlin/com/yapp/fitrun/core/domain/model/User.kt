package com.yapp.fitrun.core.domain.model

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val profileImage: String?,
    val provider: String
)