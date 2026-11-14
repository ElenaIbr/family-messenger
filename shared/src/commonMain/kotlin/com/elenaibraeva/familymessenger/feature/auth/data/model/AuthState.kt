package com.elenaibraeva.familymessenger.feature.auth.data.model

sealed interface AuthState {

    data object Initializing : AuthState

    data object Unauthenticated : AuthState

    data class Authenticated(val userId: String) : AuthState

    data object SessionUnavailable : AuthState
}
