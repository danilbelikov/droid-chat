package com.droid.chatik.api.controllers

import com.droid.chatik.api.dto.RegisterRequest
import com.droid.chatik.api.dto.UserDto
import com.droid.chatik.api.mappers.toUserDto
import com.droid.chatik.service.auth.AuthService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/auth"])
class AuthController(private val authService: AuthService) {

    @PostMapping(value = ["/register"])
    fun register(
        @Valid @RequestBody body: RegisterRequest,
    ): UserDto {
       return authService.register(
            email = body.email,
            username = body.username,
            password = body.password,
        ).toUserDto()
    }
}