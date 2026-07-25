package com.droid.chatik.api.controllers

import com.droid.chatik.api.dto.AuthenticatedUserDto
import com.droid.chatik.api.dto.LoginRequest
import com.droid.chatik.api.dto.RefreshRequest
import com.droid.chatik.api.dto.RegisterRequest
import com.droid.chatik.api.dto.UserDto
import com.droid.chatik.api.mappers.toAuthenticatedUserDto
import com.droid.chatik.api.mappers.toUserDto
import com.droid.chatik.service.auth.AuthService
import com.droid.chatik.service.auth.EmailVerificationService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/auth"])
class AuthController(private val authService: AuthService,
                     private val emailVerificationService: EmailVerificationService
) {

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

    @PostMapping(value = ["/login"])
    fun login(
        @RequestBody body: LoginRequest,
    ): AuthenticatedUserDto {
        return authService.login(
            email = body.email,
            password = body.password
        ).toAuthenticatedUserDto()
    }

    @PostMapping(value = ["/refresh"])
    fun refresh(
        @RequestBody body: RefreshRequest,
    ): AuthenticatedUserDto {
        return authService
            .refresh(body.refreshToken)
            .toAuthenticatedUserDto()
    }

    @PostMapping(value = ["/logout"])
    fun logout(
        @RequestBody body: RefreshRequest,
    ) {
        return authService.logout(body.refreshToken)
    }

    @GetMapping(value = ["/verify"])
    fun verifyEmail(
        @RequestParam token: String,
    ) {
        emailVerificationService.verifyEmail(token)
    }
}