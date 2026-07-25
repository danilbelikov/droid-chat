package com.droid.chatik.infra.database.repositories

import com.droid.chatik.infra.database.entities.EmailVerificationEntity
import com.droid.chatik.infra.database.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant

interface EmailVerificationTokenRepository : JpaRepository<EmailVerificationEntity, Long> {

    fun findByToken(token: String): EmailVerificationEntity?
    fun deleteByExpiresAtLessThan(now: Instant)
    fun findByUserAndUsedAtIsNull(user: UserEntity): List<EmailVerificationEntity>
}