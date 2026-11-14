package com.elenaibraeva.familymessenger.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elenaibraeva.familymessenger.AppViewModel
import com.elenaibraeva.familymessenger.core.designsystem.component.BrandBackground
import com.elenaibraeva.familymessenger.feature.auth.data.model.AuthState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavHost(viewModel: AppViewModel = koinViewModel()) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    when (val state = authState) {
        AuthState.Initializing -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        AuthState.Unauthenticated -> {
            BrandBackground {
                AuthNavHost()
            }
        }

        is AuthState.Authenticated -> {
            key(state.userId) {
                MainNavHost()
            }
        }

        AuthState.SessionUnavailable -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Session unavailable")
            }
        }
    }
}
