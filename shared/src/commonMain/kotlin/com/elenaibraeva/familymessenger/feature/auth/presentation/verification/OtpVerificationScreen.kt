package com.elenaibraeva.familymessenger.feature.auth.presentation.verification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
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
import com.elenaibraeva.familymessenger.feature.auth.presentation.component.OtpTextField
import com.elenaibraeva.familymessenger.feature.auth.presentation.mapper.toMessageResource
import com.elenaibraeva.familymessenger.generated.resources.Res
import com.elenaibraeva.familymessenger.generated.resources.ic_arrow_back
import com.elenaibraeva.familymessenger.generated.resources.ic_email_verification
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun OtpVerificationScreen(
    email: String,
    uiState: OtpVerificationUiState,
    onCodeChanged: (String) -> Unit,
    onVerify: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = FamilyTheme.colors
    val colorScheme = MaterialTheme.colorScheme

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val errorMessage = uiState.error?.let {
        stringResource(it.toMessageResource())
    }

    val submitCode: () -> Unit = {
        if (uiState.canVerify) {
            focusManager.clearFocus()
            keyboardController?.hide()
            onVerify()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 24.dp)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onBackClick,
            enabled = !uiState.isLoading,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(
                painter = painterResource(
                    Res.drawable.ic_arrow_back
                ),
                contentDescription = "Go back",
                tint = colorScheme.onBackground
            )
        }

        Spacer(Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .size(112.dp)
                .background(
                    color = colors.brandLogoContainer,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(
                    Res.drawable.ic_email_verification
                ),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(Modifier.height(32.dp))

        FMText(
            text = "Check your email",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = colors.brandTitle,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        FMText(
            text = "Enter the 8-digit verification code we sent to",
            style = MaterialTheme.typography.bodyLarge,
            color = colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 320.dp)
        )

        Spacer(Modifier.height(8.dp))

        FMText(
            text = email,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = colors.brandTitle,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OtpTextField(
                value = uiState.code,
                onValueChange = onCodeChanged,
                length = OtpVerificationUiState.OTP_LENGTH,
                enabled = !uiState.isLoading,
                isError = uiState.error != null,
                onDone = submitCode,
                modifier = Modifier.fillMaxWidth()
            )

            if (errorMessage != null) {
                FMText(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            FMButton(
                text = "Verify",
                onClick = submitCode,
                enabled = uiState.canVerify,
                isLoading = uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )

            TextButton(
                onClick = onBackClick,
                enabled = !uiState.isLoading
            ) {
                FMText(
                    text = "Change email address",
                    color = colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Preview(name = "OTP Verification - Light")
@Composable
private fun OtpVerificationScreenLightPreview() {
    FamilyMessengerTheme(darkTheme = false) {
        OtpVerificationScreen(
            email = "alice@example.com",
            uiState = OtpVerificationUiState(
                code = "4839"
            ),
            onCodeChanged = {},
            onVerify = {},
            onBackClick = {}
        )
    }
}

@Preview(name = "OTP Verification - Dark")
@Composable
private fun OtpVerificationScreenDarkPreview() {
    FamilyMessengerTheme(darkTheme = true) {
        OtpVerificationScreen(
            email = "alice@example.com",
            uiState = OtpVerificationUiState(
                code = "4839"
            ),
            onCodeChanged = {},
            onVerify = {},
            onBackClick = {}
        )
    }
}
