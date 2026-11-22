package com.elenaibraeva.familymessenger.feature.user.domain.model

sealed interface ProfileState {
    data object Loading : ProfileState
    data object Exists : ProfileState
    data object Missing : ProfileState
    data class Error(val message: String) : ProfileState
}