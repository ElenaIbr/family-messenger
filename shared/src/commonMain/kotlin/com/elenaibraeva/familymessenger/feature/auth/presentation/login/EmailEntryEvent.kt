package com.elenaibraeva.familymessenger.feature.auth.presentation.login

sealed interface EmailEntryEvent {

    data class NavigateToVerification(
        val email: String,
    ) : EmailEntryEvent
}
