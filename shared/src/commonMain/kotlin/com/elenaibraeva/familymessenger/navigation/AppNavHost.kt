package com.elenaibraeva.familymessenger.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
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
import com.elenaibraeva.familymessenger.feature.user.domain.model.ProfileState
import com.elenaibraeva.familymessenger.feature.user.presentation.ProfileSetupScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavHost(viewModel: AppViewModel = koinViewModel()) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val profileState by viewModel.profileState.collectAsStateWithLifecycle()

    val isCreatingProfile by viewModel.isCreatingProfile
        .collectAsStateWithLifecycle()

    val createProfileError by viewModel.createProfileError
        .collectAsStateWithLifecycle()

    when (val state = authState) {
        AuthState.Initializing -> {
            LoadingScreen()
        }

        AuthState.Unauthenticated -> {
            BrandBackground {
                AuthNavHost()
            }
        }

        is AuthState.Authenticated -> {
            key(state.userId) {
                when (profileState) {
                    ProfileState.Loading -> LoadingScreen()

                    ProfileState.Exists -> MainNavHost()

                    ProfileState.Missing -> {
                        BrandBackground {
                            ProfileSetupScreen(
                                isLoading = isCreatingProfile,
                                errorMessage = createProfileError,
                                onSubmit = viewModel::createProfile,
                            )
                        }
                    }

                    is ProfileState.Error -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text("Failed to load profile")

                            Button(onClick = viewModel::retryProfile) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }
        }

        AuthState.SessionUnavailable -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("Session unavailable")
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}
