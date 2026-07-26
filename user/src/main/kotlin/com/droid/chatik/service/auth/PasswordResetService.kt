package com.droid.chatik.service.auth

import com.droid.chatik.domain.exception.InvalidCredentialsException
import com.droid.chatik.domain.exception.InvalidTokenException
import com.droid.chatik.domain.exception.SamePasswordException
import com.droid.chatik.domain.exception.UserNotFoundException
import com.droid.chatik.domain.model.UserId
import com.droid.chatik.infra.database.entities.PasswordResetTokenEntity
import com.droid.chatik.infra.database.repositories.PasswordResetTokenRepository
import com.droid.chatik.infra.database.repositories.RefreshTokenRepository
import com.droid.chatik.infra.database.repositories.UserRepository
import com.droid.chatik.infra.security.PasswordEncoder
import io.jsonwebtoken.security.Keys.password
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class PasswordResetService(
    private val userRepository: UserRepository,
    private val passwordResetTokenRepository: PasswordResetTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    @param:Value("\${chatik.email.reset-password.expiry-minutes}")
    private val expiryMinutes: Long,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    @Transactional
    fun requestPasswordReset(email: String) {
        val user = userRepository.findByEmail(email) ?: return
        passwordResetTokenRepository.invalidateActiveTokensForUser(user)

        val token = PasswordResetTokenEntity(
            user = user,
            expiresAt = Instant.now().plus(expiryMinutes, ChronoUnit.MINUTES),
        )
        passwordResetTokenRepository.save(token)

        //todo inform notification service about password reset trigget to send email
    }

    @Transactional
    fun resetPassword(token: String, newPassword: String) {
        val resetToken = passwordResetTokenRepository.findByToken(token)
            ?: throw InvalidTokenException("Invalid reset password token")

        if (resetToken.isUsed) throw InvalidTokenException("Email verification token is already used")
        if (resetToken.isExpired) throw InvalidTokenException("Email verification token is expired")

        val user = resetToken.user

        if (passwordEncoder.matches(newPassword, user.hashedPassword)) {
            throw SamePasswordException()
        }
        val hashedPassword = passwordEncoder.encode(newPassword)
        userRepository.save(
            user.apply {
                this.hashedPassword = hashedPassword
            }
        )
        passwordResetTokenRepository.save(
            resetToken.apply {
                this.usedAt = Instant.now()
            }
        )

        refreshTokenRepository.deleteByUserId(user.id!!)
    }

    @Transactional
    fun changePassword(
        userId: UserId,
        oldPassword: String,
        newPassword: String,
    ) {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException()

        if (!passwordEncoder.matches(oldPassword, newPassword)) {
            throw InvalidCredentialsException()
        }

        if (oldPassword == newPassword) {
            throw SamePasswordException()
        }

        refreshTokenRepository.deleteByUserId(user.id!!)
        val newHashedPassword = passwordEncoder.encode(newPassword)

        userRepository.save(
            user.apply {
                this.hashedPassword = newHashedPassword
            }
        )
    }

    @Scheduled(cron = "0 0 3 * * *")
    fun cleanupExpiredTokens() {
        passwordResetTokenRepository.deleteByExpiresAtLessThan(Instant.now())
    }
}