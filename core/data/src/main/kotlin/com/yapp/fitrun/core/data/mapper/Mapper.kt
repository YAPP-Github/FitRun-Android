package com.yapp.fitrun.core.data.mapper

import com.yapp.fitrun.core.domain.enitity.LoginResultEntity
import com.yapp.fitrun.core.domain.enitity.TokenEntity
import com.yapp.fitrun.core.domain.enitity.UserEntity
import com.yapp.fitrun.core.network.model.response.LoginResponse
import com.yapp.fitrun.core.network.model.response.TokenResponse

// Network → Domain 변환
internal fun LoginResponse.toEntity() =
    LoginResultEntity(
        tokenEntity = TokenEntity(
            refreshToken = tokenResponse.refreshToken,
            accessToken = tokenResponse.accessToken
        ),
        userEntity = UserEntity(
            id = user.id,
            name = user.name,
            email = user.email,
            profileImage = user.profileImage,
            provider = user.provider
        ),
        isNewUser = isNew
    )


internal fun TokenResponse.toEntity() =
    TokenEntity(
        refreshToken = refreshToken,
        accessToken = accessToken
    )