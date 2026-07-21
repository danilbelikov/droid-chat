package com.droid.chatik.service.auth

import com.droid.chatik.api.dto.AuthenticatedUserDto
import com.droid.chatik.domain.exception.UserAlreadyExistException
import com.droid.chatik.domain.model.User
import com.droid.chatik.infra.database.entities.UserEntity
import com.droid.chatik.infra.database.mappers.toUser
import com.droid.chatik.infra.database.repositories.UserRepository
import com.droid.chatik.infra.security.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun register(email: String, username: String, password: String): User {
        val user = userRepository.findByEmailOrUsername(
            email = email.trim(),
            username = username.trim(),
        )

        if (user != null) {
            throw UserAlreadyExistException()
        }

        val savedUser = userRepository.save(
            UserEntity(
                email = email.trim(),
                username = username.trim(),
                hashedPassword = passwordEncoder.encode(password)
            )
        ).toUser()
        return savedUser
    }
}