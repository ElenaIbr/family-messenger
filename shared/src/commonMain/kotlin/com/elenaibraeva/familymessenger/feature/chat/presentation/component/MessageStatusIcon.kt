package com.elenaibraeva.familymessenger.feature.chat.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme
import com.elenaibraeva.familymessenger.feature.chat.domain.model.MessageDeliveryStatus

@Composable
internal fun MessageStatusIcon(
    status: MessageDeliveryStatus,
    modifier: Modifier = Modifier,
) {
    val color = when (status) {
        MessageDeliveryStatus.READ -> MaterialTheme.colorScheme.primary
        MessageDeliveryStatus.FAILED -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    when (status) {
        MessageDeliveryStatus.SENDING -> {
            CircularProgressIndicator(
                modifier = modifier.size(12.dp),
                strokeWidth = 1.5.dp,
                color = color,
            )
        }

        MessageDeliveryStatus.FAILED -> {
            Text(
                text = "!",
                color = color,
                style = MaterialTheme.typography.labelSmall,
            )
        }

        else -> {
            MessageCheckmarks(
                double = status == MessageDeliveryStatus.DELIVERED ||
                        status == MessageDeliveryStatus.READ,
                color = color,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun MessageCheckmarks(
    double: Boolean,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier.size(width = 18.dp, height = 12.dp)) {
        val stroke = 1.6.dp.toPx()

        fun drawCheck(offsetX: Float) {
            val width = size.width
            val height = size.height

            drawLine(
                color = color,
                start = Offset(offsetX + width * 0.05f, height * 0.52f),
                end = Offset(offsetX + width * 0.22f, height * 0.8f),
                strokeWidth = stroke,
                cap = StrokeCap.Round,
            )
            drawLine(
                color = color,
                start = Offset(offsetX + width * 0.22f, height * 0.8f),
                end = Offset(offsetX + width * 0.55f, height * 0.18f),
                strokeWidth = stroke,
                cap = StrokeCap.Round,
            )
        }

        if (double) {
            drawCheck(0f)
            drawCheck(size.width * 0.35f)
        } else {
            drawCheck(size.width * 0.2f)
        }
    }
}

@Preview(name = "Message Status - Light")
@Composable
private fun MessageStatusLightPreview() {
    FamilyMessengerTheme(darkTheme = false) {
        MessageStatusPreviewContent()
    }
}

@Preview(name = "Message Status - Dark")
@Composable
private fun MessageStatusDarkPreview() {
    FamilyMessengerTheme(darkTheme = true) {
        MessageStatusPreviewContent()
    }
}

@Composable
private fun MessageStatusPreviewContent() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        MessageDeliveryStatus.entries.forEach { status ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Box(
                    modifier = Modifier.width(32.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    MessageStatusIcon(status = status)
                }

                Text(
                    text = status.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}
