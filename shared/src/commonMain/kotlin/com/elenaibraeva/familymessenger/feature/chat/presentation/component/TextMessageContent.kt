package com.elenaibraeva.familymessenger.feature.chat.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.elenaibraeva.familymessenger.core.designsystem.component.FMText

@Composable
internal fun TextMessageContent(text: String, color: Color) {
    FMText(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = color
    )
}
