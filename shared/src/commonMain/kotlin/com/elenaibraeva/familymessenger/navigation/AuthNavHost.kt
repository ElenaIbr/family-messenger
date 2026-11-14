package com.elenaibraeva.familymessenger.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.elenaibraeva.familymessenger.feature.auth.presentation.login.EmailEntryRoute
import com.elenaibraeva.familymessenger.feature.auth.presentation.verification.OtpVerificationRoute

@Composable
fun AuthNavHost() {
    val backStack = rememberNavBackStack(
        configuration = navigationSavedStateConfiguration,
        Destination.Auth.EmailEntry,
    )

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {

            entry<Destination.Auth.EmailEntry> {
                EmailEntryRoute(
                    onNavigateToVerification = { email ->
                        backStack.add(
                            Destination.Auth.OtpVerification(email)
                        )
                    },
                )
            }

            entry<Destination.Auth.OtpVerification> { destination ->
                OtpVerificationRoute(
                    email = destination.email,
                    onBackClick = {
                        if (backStack.size > 1) {
                            backStack.removeAt(backStack.lastIndex)
                        }
                    },
                )
            }
        },
    )
}
