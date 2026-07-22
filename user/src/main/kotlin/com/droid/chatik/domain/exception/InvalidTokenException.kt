package com.droid.chatik.domain.exception

class InvalidTokenException(
    override val message: String?
) : RuntimeException(
    message ?: "Invalid token"
)