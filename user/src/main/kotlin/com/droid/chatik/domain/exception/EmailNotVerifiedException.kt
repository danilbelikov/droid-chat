package com.droid.chatik.domain.exception

class EmailNotVerifiedException: RuntimeException(
    "Email is not verified. Please verify your email address."
)