package com.droid.chatik.domain.exception

class SamePasswordException: RuntimeException(
    "The new password can't be equal to the same password."
) {
}