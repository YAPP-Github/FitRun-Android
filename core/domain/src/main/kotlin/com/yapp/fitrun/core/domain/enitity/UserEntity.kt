package com.yapp.fitrun.core.domain.enitity

data class UserEntity(
    val id: Long,
    val name: String,
    val email: String,
    val profileImage: String?,
    val provider: String
)