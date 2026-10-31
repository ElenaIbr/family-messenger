package com.elenaibraeva.familymessenger.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme

@Composable
fun FMTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            isError = isError,
            singleLine = singleLine,
            label = label?.let {
                { FMText(text = it) }
            },
            placeholder = placeholder?.let {
                { FMText(text = it) }
            },
            keyboardOptions = keyboardOptions,
            shape = MaterialTheme.shapes.medium
        )

        if (isError && !errorMessage.isNullOrBlank()) {
            FMText(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Preview(name = "FMTextField - Light")
@Composable
private fun FMTextFieldLightPreview() {
    FamilyMessengerTheme(darkTheme = false) {
        FMTextFieldPreviewContent()
    }
}

@Preview(name = "FMTextField - Dark")
@Composable
private fun FMTextFieldDarkPreview() {
    FamilyMessengerTheme(darkTheme = true) {
        FMTextFieldPreviewContent()
    }
}

@Composable
private fun FMTextFieldPreviewContent() {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FMTextField(
                value = "",
                onValueChange = {},
                label = "Email",
                placeholder = "Enter your email",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            FMTextField(
                value = "elena@example.com",
                onValueChange = {},
                label = "Email"
            )

            FMTextField(
                value = "invalid-email",
                onValueChange = {},
                label = "Email",
                isError = true,
                errorMessage = "Please enter a valid email address"
            )

            FMTextField(
                value = "Disabled field",
                onValueChange = {},
                label = "Email",
                enabled = false
            )
        }
    }
}
