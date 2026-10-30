package com.elenaibraeva.familymessenger.core.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme

@Composable
fun FMButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = MaterialTheme.shapes.large,
        contentPadding = PaddingValues(
            horizontal = 24.dp,
            vertical = 12.dp,
        ),
    ) {
        FMText(
            text = text,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Preview(
    name = "FMButton - Light",
    showBackground = true,
)
@Composable
private fun FMButtonLightPreview() {
    FamilyMessengerTheme(darkTheme = false) {
        FMButtonPreviewContent()
    }
}

@Preview(
    name = "FMButton - Dark",
    showBackground = true,
)
@Composable
private fun FMButtonDarkPreview() {
    FamilyMessengerTheme(darkTheme = true) {
        FMButtonPreviewContent()
    }
}

@Composable
private fun FMButtonPreviewContent() {
    Column(
        modifier = Modifier.padding(24.dp),
    ) {
        FMButton(
            text = "Continue",
            onClick = {},
        )

        Spacer(modifier = Modifier.height(16.dp))

        FMButton(
            text = "Continue",
            onClick = {},
            enabled = false,
        )
    }
}
