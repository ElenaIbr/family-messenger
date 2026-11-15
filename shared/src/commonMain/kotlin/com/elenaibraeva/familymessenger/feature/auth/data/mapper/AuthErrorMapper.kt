package com.elenaibraeva.familymessenger.feature.auth.data.mapper

import com.elenaibraeva.familymessenger.feature.auth.domain.model.AuthError
import com.elenaibraeva.familymessenger.feature.auth.domain.model.AuthException
import io.github.jan.supabase.auth.exception.AuthErrorCode
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException

internal fun Exception.toAuthException(): AuthException {
    if (this is AuthException) return this

    val authError = when (this) {
        is AuthRestException -> when (errorCode) {
            AuthErrorCode.OverEmailSendRateLimit ->
                AuthError.EMAIL_RATE_LIMIT

            AuthErrorCode.OverRequestRateLimit ->
                AuthError.REQUEST_RATE_LIMIT

            AuthErrorCode.OtpExpired ->
                AuthError.INVALID_OR_EXPIRED_OTP

            AuthErrorCode.EmailAddressInvalid ->
                AuthError.INVALID_EMAIL

            else -> mapHttpStatus(response.status.value)
        }

        is RestException ->
            mapHttpStatus(response.status.value)

        is HttpRequestException ->
            AuthError.NETWORK

        else ->
            AuthError.UNKNOWN
    }

    return AuthException(
        error = authError,
        cause = this,
    )
}

private fun mapHttpStatus(status: Int): AuthError =
    when (status) {
        429 -> AuthError.REQUEST_RATE_LIMIT
        in 500..599 -> AuthError.SERVER
        else -> AuthError.UNKNOWN
    }
