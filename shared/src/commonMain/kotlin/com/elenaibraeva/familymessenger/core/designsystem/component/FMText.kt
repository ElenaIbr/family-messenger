package com.elenaibraeva.familymessenger.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme

@Composable
fun FMText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
    )
}

@Preview(name = "FMText - Light")
@Composable
private fun FMTextLightPreview() {
    FamilyMessengerTheme(darkTheme = false) {
        FMTextPreviewContent()
    }
}

@Preview(name = "FMText - Dark")
@Composable
private fun FMTextDarkPreview() {
    FamilyMessengerTheme(darkTheme = true) {
        FMTextPreviewContent()
    }
}

@Composable
private fun FMTextPreviewContent() {
    Surface(
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            FMText(
                text = "Family Messenger",
                style = MaterialTheme.typography.headlineLarge,
            )

            FMText(
                text = "Your conversations",
                style = MaterialTheme.typography.titleMedium,
            )

            FMText(
                text = "Stay connected with your family.",
                style = MaterialTheme.typography.bodyMedium,
            )

            FMText(
                text = "Last seen recently",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
