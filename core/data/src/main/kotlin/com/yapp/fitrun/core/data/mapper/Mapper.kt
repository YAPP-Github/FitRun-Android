package com.yapp.fitrun.core.data.mapper

import com.yapp.fitrun.core.domain.model.LoginResult
import com.yapp.fitrun.core.domain.model.User
import com.yapp.fitrun.core.network.model.response.LoginResponse

// Network → Domain 변환
fun LoginResponse.toDomainModel(): LoginResult {
    return LoginResult(
        user = User(
            id = user.id,
            name = user.name,
            email = user.email,
            profileImage = user.profileImage,
            provider = user.provider
        ),
        isNewUser = isNew
    )
}