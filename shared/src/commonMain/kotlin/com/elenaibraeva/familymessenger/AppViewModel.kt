package com.elenaibraeva.familymessenger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elenaibraeva.familymessenger.feature.auth.data.model.AuthState
import com.elenaibraeva.familymessenger.feature.auth.domain.repository.AuthRepository
import com.elenaibraeva.familymessenger.feature.user.domain.model.ProfileState
import com.elenaibraeva.familymessenger.feature.user.domain.repository.ProfileRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(
    authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
) : ViewModel() {

    val authState = authRepository.authState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = AuthState.Initializing,
    )

    private val _profileState = MutableStateFlow<ProfileState>(
        ProfileState.Loading,
    )
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    init {
        viewModelScope.launch {
            authState.collectLatest { state ->
                when (state) {
                    is AuthState.Authenticated -> {
                        loadProfile()
                    }

                    else -> {
                        _profileState.value = ProfileState.Loading
                    }
                }
            }
        }
    }

    fun retryProfile() {
        if (authState.value !is AuthState.Authenticated) return

        viewModelScope.launch {
            loadProfile()
        }
    }

    private suspend fun loadProfile() {
        _profileState.value = ProfileState.Loading

        try {
            val profile = profileRepository.getMe()

            _profileState.value = if (profile != null) {
                ProfileState.Exists
            } else {
                ProfileState.Missing
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val status = (e as? io.ktor.client.plugins.ResponseException)
                ?.response
                ?.status

            println(
                "Profile loading failed: " +
                        "status=$status, " +
                        "error=${e::class.simpleName}, " +
                        "message=${e.message}, " +
                        "cause=${e.cause?.message}"
            )

            _profileState.value = ProfileState.Error(
                message = "Failed to load profile",
            )
        }
    }
}
