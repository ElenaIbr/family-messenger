package com.elenaibraeva.familymessenger.feature.auth.domain.service

import com.elenaibraeva.familymessenger.feature.auth.domain.model.OtpRequestResult
import com.elenaibraeva.familymessenger.feature.auth.domain.repository.AuthRepository
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeMark
import kotlin.time.TimeSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class OtpRequestCoordinator(private val authRepository: AuthRepository) {
    private val mutex = Mutex()

    private val lastRequests = mutableMapOf<String, TimeMark>()

    suspend fun requestOtp(email: String): OtpRequestResult = mutex.withLock {
        val normalizedEmail = email.trim()
        val key = normalizedEmail.lowercase()

        val remaining = remainingDuration(key)

        if (remaining > Duration.ZERO) {
            return@withLock OtpRequestResult.CooldownActive(
                remainingSeconds = remaining.toSecondsRoundedUp()
            )
        }

        authRepository.requestOtp(normalizedEmail)

        lastRequests[key] = TimeSource.Monotonic.markNow()

        OtpRequestResult.Sent
    }

    suspend fun remainingSeconds(email: String): Int = mutex.withLock {
        remainingDuration(email.trim().lowercase())
            .toSecondsRoundedUp()
    }

    private fun remainingDuration(email: String): Duration {
        val lastRequest = lastRequests[email]
            ?: return Duration.ZERO

        return (COOLDOWN - lastRequest.elapsedNow())
            .coerceAtLeast(Duration.ZERO)
    }

    private fun Duration.toSecondsRoundedUp(): Int = ((inWholeMilliseconds + 999) / 1000).toInt()

    private companion object {
        val COOLDOWN = 60.seconds
    }
}
