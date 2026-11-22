package com.elenaibraeva.familymessenger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elenaibraeva.familymessenger.feature.auth.data.model.AuthState
import com.elenaibraeva.familymessenger.feature.auth.domain.repository.AuthRepository
import com.elenaibraeva.familymessenger.feature.user.data.model.CreateUserRequest
import com.elenaibraeva.familymessenger.feature.user.domain.model.ProfileState
import com.elenaibraeva.familymessenger.feature.user.domain.repository.ProfileRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
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

    private val _isCreatingProfile = MutableStateFlow(false)
    val isCreatingProfile: StateFlow<Boolean> =
        _isCreatingProfile.asStateFlow()

    private val _createProfileError = MutableStateFlow<String?>(null)
    val createProfileError: StateFlow<String?> =
        _createProfileError.asStateFlow()

    private var createProfileJob: Job? = null

    init {
        viewModelScope.launch {
            authState.collectLatest { state ->
                createProfileJob?.cancel()
                _isCreatingProfile.value = false
                _createProfileError.value = null

                when (state) {
                    is AuthState.Authenticated -> loadProfile()

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

    fun createProfile(
        firstName: String,
        lastName: String?,
        dateOfBirth: String?,
    ) {
        val currentUser = authState.value as? AuthState.Authenticated
            ?: return

        if (_profileState.value != ProfileState.Missing) return
        if (createProfileJob?.isActive == true) return

        createProfileJob = viewModelScope.launch {
            _isCreatingProfile.value = true
            _createProfileError.value = null

            try {
                profileRepository.createMe(
                    CreateUserRequest(
                        firstName = firstName,
                        lastName = lastName,
                        dateOfBirth = dateOfBirth,
                    )
                )

                if (authState.value == currentUser) {
                    _profileState.value = ProfileState.Exists
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                if (authState.value == currentUser) {
                    _createProfileError.value =
                        "Failed to create profile. Please try again."
                }
            } finally {
                _isCreatingProfile.value = false
            }
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
            _profileState.value = ProfileState.Error(
                message = "Failed to load profile",
            )
        }
    }
}
