package com.droid.chatik.infra.database.mappers

import com.droid.chatik.domain.model.User
import com.droid.chatik.infra.database.entities.UserEntity

fun UserEntity.toUser() = User(
    id = id!!,
    username = username,
    hasEmailVerified = hasVerifiedEmail,
    email = email,
)