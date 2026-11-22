package com.elenaibraeva.familymessenger.feature.user.presentation

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.core.designsystem.component.BrandBackground
import com.elenaibraeva.familymessenger.core.designsystem.component.FMButton
import com.elenaibraeva.familymessenger.core.designsystem.component.FMText
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyTheme
import com.elenaibraeva.familymessenger.generated.resources.Res
import com.elenaibraeva.familymessenger.generated.resources.ic_profile_setup
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProfileSetupScreen(
    isLoading: Boolean,
    errorMessage: String?,
    onSubmit: (
        firstName: String,
        lastName: String?,
        dateOfBirth: String?,
    ) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = FamilyTheme.colors
    val colorScheme = MaterialTheme.colorScheme

    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var dateOfBirth by rememberSaveable { mutableStateOf("") }

    val first = firstName.trim()
    val last = lastName.trim()
    val birthDate = dateOfBirth.trim()

    val isDateValid = birthDate.isEmpty() ||
            Regex("""\d{4}-\d{2}-\d{2}""").matches(birthDate)

    val canSubmit = first.isNotEmpty() &&
            first.length <= 100 &&
            last.length <= 100 &&
            isDateValid &&
            !isLoading

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(112.dp)
                .background(
                    color = colors.brandLogoContainer,
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_profile_setup),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
            )
        }

        Spacer(Modifier.height(32.dp))

        FMText(
            text = "Create your profile",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
            ),
            color = colors.brandTitle,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(16.dp))

        FMText(
            text = "Tell us a little about yourself to get started.",
            style = MaterialTheme.typography.bodyLarge,
            color = colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 320.dp),
        )

        Spacer(Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First name *") },
                singleLine = true,
                enabled = !isLoading,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last name (optional)") },
                singleLine = true,
                enabled = !isLoading,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = dateOfBirth,
                onValueChange = { dateOfBirth = it },
                label = { Text("Date of birth (optional)") },
                placeholder = { Text("YYYY-MM-DD") },
                singleLine = true,
                enabled = !isLoading,
                isError = !isDateValid,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                supportingText = {
                    Text(
                        if (isDateValid) {
                            "Your birthday is private by default"
                        } else {
                            "Use YYYY-MM-DD format"
                        },
                    )
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
            )

            if (errorMessage != null) {
                FMText(
                    text = errorMessage,
                    color = colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Spacer(Modifier.height(8.dp))

            FMButton(
                text = "Continue",
                onClick = {
                    onSubmit(
                        first,
                        last.takeIf { it.isNotEmpty() },
                        birthDate.takeIf { it.isNotEmpty() },
                    )
                },
                enabled = canSubmit,
                isLoading = isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            )
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Preview(name = "Profile Setup - Light")
@Composable
private fun ProfileSetupLightPreview() {
    FamilyMessengerTheme(darkTheme = false) {
        BrandBackground {
            ProfileSetupScreen(
                isLoading = false,
                errorMessage = null,
                onSubmit = { _, _, _ -> },
            )
        }
    }
}

@Preview(name = "Profile Setup - Dark")
@Composable
private fun ProfileSetupDarkPreview() {
    FamilyMessengerTheme(darkTheme = true) {
        BrandBackground {
            ProfileSetupScreen(
                isLoading = false,
                errorMessage = null,
                onSubmit = { _, _, _ -> },
            )
        }
    }
}

@Preview(name = "Profile Setup - Error")
@Composable
private fun ProfileSetupErrorPreview() {
    FamilyMessengerTheme(darkTheme = false) {
        BrandBackground {
            ProfileSetupScreen(
                isLoading = false,
                errorMessage = "Failed to create profile. Please try again.",
                onSubmit = { _, _, _ -> },
            )
        }
    }
}
