package com.elenaibraeva.familymessenger.feature.auth.presentation.verification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.core.designsystem.component.FMButton
import com.elenaibraeva.familymessenger.core.designsystem.component.FMText
import com.elenaibraeva.familymessenger.core.designsystem.component.FMTextField
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme
import com.elenaibraeva.familymessenger.generated.resources.Res
import com.elenaibraeva.familymessenger.generated.resources.ic_family_messenger
import org.jetbrains.compose.resources.painterResource

@Composable
fun OtpVerificationScreen(
    email: String,
    uiState: OtpVerificationUiState,
    onCodeChanged: (String) -> Unit,
    onVerify: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(32.dp))

        Image(
            painter = painterResource(
                Res.drawable.ic_family_messenger
            ),
            contentDescription = "Family Messenger",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.Start),
        )

        Spacer(Modifier.height(48.dp))

        Column(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            FMText(
                text = "Check your email",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onBackground,
            )

            FMText(
                text = "Enter the 8-digit verification code we sent to",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            FMText(
                text = email,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
            )

            Spacer(Modifier.height(20.dp))

            FMText(
                text = "Verification code",
                style = MaterialTheme.typography.labelLarge,
            )

            FMTextField(
                value = uiState.code,
                onValueChange = onCodeChanged,
                placeholder = "00000000",
                enabled = !uiState.isLoading,
                isError = uiState.error != null,
                errorMessage = uiState.error,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(4.dp))

            FMButton(
                text = "Verify",
                onClick = onVerify,
                enabled = uiState.canVerify,
                isLoading = uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            )

            TextButton(
                onClick = onBackClick,
                enabled = !uiState.isLoading,
                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                ),
            ) {
                FMText(text = "Change email address")
            }
        }
    }
}

@Preview
@Composable
private fun OtpVerificationScreenPreview() {
    FamilyMessengerTheme {
        OtpVerificationScreen(
            email = "alice@example.com",
            uiState = OtpVerificationUiState(
                code = "4839",
            ),
            onCodeChanged = {},
            onVerify = {},
            onBackClick = {},
        )
    }
}
