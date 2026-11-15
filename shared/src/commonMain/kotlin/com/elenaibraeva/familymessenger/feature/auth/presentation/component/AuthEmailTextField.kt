package com.elenaibraeva.familymessenger.feature.auth.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.core.designsystem.component.FMText

@Composable
fun AuthEmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    errorMessage: String? = null,
    onFocusChanged: (Boolean) -> Unit = {},
    onSend: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme
    val shape = RoundedCornerShape(20.dp)

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .clip(shape)
            .onFocusChanged {
                onFocusChanged(it.isFocused)
            },
        enabled = enabled,
        singleLine = true,
        isError = errorMessage != null,
        shape = shape,
        placeholder = {
            FMText(
                text = "Email address",
                color = colors.onSurfaceVariant
            )
        },
        leadingIcon = {
            EmailIcon(
                color = colors.onSurfaceVariant
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Send
        ),
        keyboardActions = KeyboardActions(
            onSend = { onSend() }
        ),
        supportingText = errorMessage?.let { message ->
            {
                FMText(
                    text = message,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.error
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colors.surface,
            unfocusedContainerColor = colors.surface,
            disabledContainerColor = colors.surface.copy(
                alpha = 0.7f
            ),
            errorContainerColor = colors.surface,

            focusedTextColor = colors.onSurface,
            unfocusedTextColor = colors.onSurface,
            disabledTextColor = colors.onSurface.copy(
                alpha = 0.38f
            ),
            errorTextColor = colors.onSurface,

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,

            cursorColor = colors.primary,
            errorCursorColor = colors.error
        )
    )
}

@Composable
private fun EmailIcon(color: Color) {
    Canvas(
        modifier = Modifier.size(22.dp)
    ) {
        val strokeWidth = 1.8.dp.toPx()

        drawRoundRect(
            color = color,
            topLeft = Offset(
                x = size.width * 0.08f,
                y = size.height * 0.18f
            ),
            size = Size(
                width = size.width * 0.84f,
                height = size.height * 0.64f
            ),
            cornerRadius = CornerRadius(
                3.dp.toPx()
            ),
            style = Stroke(
                width = strokeWidth
            )
        )

        drawLine(
            color = color,
            start = Offset(
                size.width * 0.10f,
                size.height * 0.25f
            ),
            end = Offset(
                size.width * 0.50f,
                size.height * 0.52f
            ),
            strokeWidth = strokeWidth
        )

        drawLine(
            color = color,
            start = Offset(
                size.width * 0.50f,
                size.height * 0.52f
            ),
            end = Offset(
                size.width * 0.90f,
                size.height * 0.25f
            ),
            strokeWidth = strokeWidth
        )
    }
}
