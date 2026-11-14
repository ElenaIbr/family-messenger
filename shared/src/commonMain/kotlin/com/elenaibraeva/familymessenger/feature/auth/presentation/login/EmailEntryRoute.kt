package com.elenaibraeva.familymessenger.feature.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EmailEntryRoute(
    onNavigateToVerification: (String) -> Unit,
    viewModel: EmailEntryViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                is EmailEntryEvent.NavigateToVerification ->
                    onNavigateToVerification(event.email)
            }
        }
    }

    EmailEntryScreen(
        uiState = uiState,
        onEmailChanged = viewModel::onEmailChanged,
        onContinue = viewModel::onContinue,
    )
}
