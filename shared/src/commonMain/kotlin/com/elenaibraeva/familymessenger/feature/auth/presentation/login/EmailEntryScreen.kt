package com.elenaibraeva.familymessenger.feature.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.core.designsystem.component.FMButton
import com.elenaibraeva.familymessenger.core.designsystem.component.FMText
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyTheme
import com.elenaibraeva.familymessenger.feature.auth.presentation.component.AuthEmailTextField
import com.elenaibraeva.familymessenger.feature.auth.presentation.component.FamilyOrbitIllustration

@Composable
fun EmailEntryScreen(
    uiState: EmailEntryUiState,
    onEmailChanged: (String) -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
    animateIllustration: Boolean = true
) {
    val colors = FamilyTheme.colors
    val colorScheme = MaterialTheme.colorScheme

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val submitEmail: () -> Unit = {
        if (uiState.canContinue) {
            focusManager.clearFocus()
            keyboardController?.hide()
            onContinue()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp
            )
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))

        FamilyOrbitIllustration(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .fillMaxWidth(),
            animate = animateIllustration
        )

        Spacer(Modifier.height(24.dp))

        FMText(
            text = "Family Messenger",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = colors.brandTitle,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        FMText(
            text = "Stay close to the people who matter most.",
            style = MaterialTheme.typography.bodyLarge,
            color = colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 320.dp)
        )

        Spacer(Modifier.height(48.dp))

        Column(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AuthEmailTextField(
                value = uiState.email,
                onValueChange = onEmailChanged,
                onSend = submitEmail,
                enabled = !uiState.isLoading,
                errorMessage = uiState.error,
                modifier = Modifier.fillMaxWidth()
            )

            FMButton(
                text = "Continue",
                onClick = submitEmail,
                enabled = uiState.canContinue,
                isLoading = uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Preview(name = "Email Entry - Light")
@Composable
private fun EmailEntryScreenLightPreview() {
    FamilyMessengerTheme(darkTheme = false) {
        EmailEntryScreen(
            uiState = EmailEntryUiState(
                email = "alice@example.com"
            ),
            onEmailChanged = {},
            onContinue = {},
            animateIllustration = false
        )
    }
}

@Preview(name = "Email Entry - Dark")
@Composable
private fun EmailEntryScreenDarkPreview() {
    FamilyMessengerTheme(darkTheme = true) {
        EmailEntryScreen(
            uiState = EmailEntryUiState(
                email = "alice@example.com"
            ),
            onEmailChanged = {},
            onContinue = {},
            animateIllustration = false
        )
    }
}
