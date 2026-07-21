package com.droid.chatik.infra.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoder {

    private val bcryptPasswordEncoder = BCryptPasswordEncoder()

    fun encode(rawPassword: String): String =
        requireNotNull(bcryptPasswordEncoder.encode(rawPassword)) { "BCrypt вернул null" }

    fun matches(rawPassword: String, hashedPassword: String): Boolean =
        bcryptPasswordEncoder.matches(rawPassword, hashedPassword)

}