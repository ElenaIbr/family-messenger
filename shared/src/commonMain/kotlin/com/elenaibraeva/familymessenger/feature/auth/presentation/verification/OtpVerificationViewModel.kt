package com.elenaibraeva.familymessenger.feature.auth.presentation.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elenaibraeva.familymessenger.feature.auth.domain.model.AuthError
import com.elenaibraeva.familymessenger.feature.auth.domain.model.AuthException
import com.elenaibraeva.familymessenger.feature.auth.domain.model.OtpRequestResult
import com.elenaibraeva.familymessenger.feature.auth.domain.repository.AuthRepository
import com.elenaibraeva.familymessenger.feature.auth.domain.service.OtpRequestCoordinator
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class OtpVerificationViewModel(
    private val email: String,
    private val authRepository: AuthRepository,
    private val otpRequestCoordinator: OtpRequestCoordinator
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        OtpVerificationUiState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            while (isActive) {
                refreshCooldown()
                delay(1_000.milliseconds)
            }
        }
    }

    fun onCodeChanged(code: String) {
        if (_uiState.value.isLoading || _uiState.value.isResending) {
            return
        }

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
            } catch (e: AuthException) {
                _uiState.update {
                    it.copy(error = e.error)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = AuthError.UNKNOWN)
                }
            } finally {
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    fun onResendCode() {
        if (!_uiState.value.canResend) return

        _uiState.update {
            it.copy(
                isResending = true,
                error = null
            )
        }

        viewModelScope.launch {
            try {
                when (val result = otpRequestCoordinator.requestOtp(email)) {
                    OtpRequestResult.Sent -> {
                        _uiState.update {
                            it.copy(
                                code = "",
                                error = null
                            )
                        }
                    }

                    is OtpRequestResult.CooldownActive -> {
                        _uiState.update {
                            it.copy(
                                resendCooldownSeconds = result.remainingSeconds
                            )
                        }
                    }
                }

                refreshCooldown()
            } catch (e: CancellationException) {
                throw e
            } catch (e: AuthException) {
                _uiState.update {
                    it.copy(error = e.error)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = AuthError.UNKNOWN)
                }
            } finally {
                _uiState.update {
                    it.copy(isResending = false)
                }
            }
        }
    }

    private suspend fun refreshCooldown() {
        val remainingSeconds =
            otpRequestCoordinator.remainingSeconds(email)

        _uiState.update {
            it.copy(resendCooldownSeconds = remainingSeconds)
        }
    }
}
