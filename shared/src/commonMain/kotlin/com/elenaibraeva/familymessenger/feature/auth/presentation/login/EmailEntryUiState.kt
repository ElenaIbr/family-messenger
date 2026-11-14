package com.elenaibraeva.familymessenger.feature.auth.presentation.login

data class EmailEntryUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
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
