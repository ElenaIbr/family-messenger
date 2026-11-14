package com.elenaibraeva.familymessenger.feature.auth.presentation.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elenaibraeva.familymessenger.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OtpVerificationViewModel(
    private val email: String,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        OtpVerificationUiState()
    )
    val uiState = _uiState.asStateFlow()

    fun onCodeChanged(code: String) {
        if (_uiState.value.isLoading) return

        _uiState.update {
            it.copy(
                code = code
                    .filter { char -> char in '0'..'9' }
                    .take(OtpVerificationUiState.OTP_LENGTH),
                error = null
            )
        }
    }

    fun onVerify() {
        val state = _uiState.value

        if (!state.canVerify) return

        _uiState.update {
            it.copy(isLoading = true, error = null)
        }

        viewModelScope.launch {
            try {
                authRepository.verifyOtp(
                    email = email,
                    code = state.code
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = "Unable to verify code. Please try again."
                    )
                }
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }
}
