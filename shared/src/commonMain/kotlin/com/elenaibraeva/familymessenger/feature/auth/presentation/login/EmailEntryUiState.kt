package com.elenaibraeva.familymessenger.feature.auth.presentation.login

import com.elenaibraeva.familymessenger.feature.auth.domain.model.AuthError

data class EmailEntryUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val error: AuthError? = null
) {

    val isEmailValid: Boolean
        get() = EMAIL_REGEX.matches(email.trim())

    val canContinue: Boolean
        get() = isEmailValid && !isLoading

    private companion object {
        val EMAIL_REGEX = Regex(
            "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"
        )
    }
}
