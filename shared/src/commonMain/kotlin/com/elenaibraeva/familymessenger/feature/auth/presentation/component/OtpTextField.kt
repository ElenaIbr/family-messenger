package com.elenaibraeva.familymessenger.feature.auth.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.core.designsystem.component.FMText
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme

@Composable
fun OtpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    length: Int = 8,
    enabled: Boolean = true,
    isError: Boolean = false,
    onDone: () -> Unit = {}
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    BasicTextField(
        value = value,
        onValueChange = { input ->
            onValueChange(
                input
                    .filter { it in '0'..'9' }
                    .take(length)
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                isFocused = it.isFocused
            },
        enabled = enabled,
        singleLine = true,
        textStyle = MaterialTheme.typography.titleLarge.copy(
            color = Color.Transparent
        ),
        cursorBrush = SolidColor(Color.Transparent),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        ),
        decorationBox = { innerTextField ->
            Box {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    repeat(length) { index ->
                        val char = value.getOrNull(index)

                        OtpCell(
                            value = char?.toString().orEmpty(),
                            isActive = isFocused &&
                                index == value.length,
                            isError = isError,
                            enabled = enabled,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                innerTextField()
            }
        }
    )
}

@Composable
private fun OtpCell(
    value: String,
    isActive: Boolean,
    isError: Boolean,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val shape = RoundedCornerShape(12.dp)

    val borderColor = when {
        isError -> colors.error
        isActive -> colors.primary
        else -> colors.outlineVariant
    }

    Box(
        modifier = modifier
            .height(56.dp)
            .background(
                color = if (enabled) {
                    colors.surface
                } else {
                    colors.surface.copy(alpha = 0.6f)
                },
                shape = shape
            )
            .border(
                width = if (isActive) 2.dp else 1.dp,
                color = borderColor,
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        FMText(
            text = value,
            color = colors.onSurface,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Preview(name = "OTP - Light")
@Composable
private fun FMOtpTextFieldLightPreview() {
    FamilyMessengerTheme(darkTheme = false) {
        OtpTextField(
            value = "4839",
            onValueChange = {}
        )
    }
}

@Preview(name = "OTP - Dark")
@Composable
private fun FMOtpTextFieldDarkPreview() {
    FamilyMessengerTheme(darkTheme = true) {
        OtpTextField(
            value = "4839",
            onValueChange = {}
        )
    }
}

@Preview(name = "OTP - Error")
@Composable
private fun FMOtpTextFieldErrorPreview() {
    FamilyMessengerTheme(darkTheme = false) {
        OtpTextField(
            value = "12345678",
            onValueChange = {},
            isError = true
        )
    }
}
