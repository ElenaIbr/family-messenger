package com.elenaibraeva.familymessenger.feature.auth.presentation.verification

import com.elenaibraeva.familymessenger.feature.auth.domain.model.AuthError

data class OtpVerificationUiState(
    val code: String = "",
    val isLoading: Boolean = false,
    val error: AuthError? = null
) {
    val canVerify: Boolean
        get() = code.length == OTP_LENGTH && !isLoading

    companion object {
        const val OTP_LENGTH = 8
    }
}
