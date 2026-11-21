package com.elenaibraeva.familymessenger.feature.auth.presentation.verification

import com.elenaibraeva.familymessenger.feature.auth.domain.model.AuthError

data class OtpVerificationUiState(
    val code: String = "",
    val isLoading: Boolean = false,
    val isResending: Boolean = false,
    val resendCooldownSeconds: Int = 0,
    val error: AuthError? = null
) {
    val canVerify: Boolean
        get() = code.length == OTP_LENGTH &&
            !isLoading &&
            !isResending

    val canResend: Boolean
        get() = resendCooldownSeconds == 0 &&
            !isLoading &&
            !isResending

    companion object {
        const val OTP_LENGTH = 8
    }
}
