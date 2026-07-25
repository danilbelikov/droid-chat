package com.droid.chatik.infra.database.mappers

import com.droid.chatik.domain.model.EmailVerificationToken
import com.droid.chatik.infra.database.entities.EmailVerificationEntity

fun EmailVerificationEntity.toEmailVerificationToken(): EmailVerificationToken {
    return EmailVerificationToken(
        id = id,
        token = token,
        user  = user.toUser(),
    )
}