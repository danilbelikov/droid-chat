package com.droid.chatik.domain.exception

class UserAlreadyExistException : RuntimeException(
    "A user with this username or email already exists"
)