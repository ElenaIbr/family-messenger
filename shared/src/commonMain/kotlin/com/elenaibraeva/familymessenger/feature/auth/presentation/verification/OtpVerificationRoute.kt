package com.elenaibraeva.familymessenger.feature.auth.presentation.verification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun OtpVerificationRoute(email: String, onBackClick: () -> Unit) {
    val viewModel: OtpVerificationViewModel = koinViewModel(
        key = email,
        parameters = { parametersOf(email) }
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    OtpVerificationScreen(
        email = email,
        uiState = uiState,
        onCodeChanged = viewModel::onCodeChanged,
        onVerify = viewModel::onVerify,
        onBackClick = onBackClick
    )
}
