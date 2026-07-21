package com.droid.chatik.api.mappers

import com.droid.chatik.api.dto.AuthenticatedUserDto
import com.droid.chatik.api.dto.UserDto
import com.droid.chatik.domain.model.AuthenticatedUser
import com.droid.chatik.domain.model.User


fun AuthenticatedUser.toAuthenticatedUserDto(): AuthenticatedUserDto {
    return AuthenticatedUserDto(
        user = user.toUserDto(),
        accessToken = accessToken,
        refreshToken = refreshToken,
    )
}

fun User.toUserDto(): UserDto {
    return UserDto(
        id = id,
        email = email,
        username = username,
        hasVerifiedEmail = hasEmailVerified
    )
}