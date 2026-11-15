package com.elenaibraeva.familymessenger.feature.auth.domain.model

enum class AuthError {
    EMAIL_RATE_LIMIT,
    REQUEST_RATE_LIMIT,
    INVALID_OR_EXPIRED_OTP,
    INVALID_EMAIL,
    NETWORK,
    SERVER,
    UNKNOWN,
}
