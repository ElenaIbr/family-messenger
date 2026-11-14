package com.elenaibraeva.familymessenger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elenaibraeva.familymessenger.feature.auth.data.model.AuthState
import com.elenaibraeva.familymessenger.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class AppViewModel(authRepository: AuthRepository) : ViewModel() {

    val authState = authRepository.authState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = AuthState.Initializing
    )
}
