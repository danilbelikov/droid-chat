package com.droid.chatik.domain.exception

class InvalidCredentialsException : RuntimeException(
    "The entered credentials is invalid. Please try again later."
) {

}