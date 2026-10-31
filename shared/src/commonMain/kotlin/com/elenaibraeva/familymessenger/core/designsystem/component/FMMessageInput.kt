package com.elenaibraeva.familymessenger.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme
import com.elenaibraeva.familymessenger.generated.resources.Res
import com.elenaibraeva.familymessenger.generated.resources.ic_send
import org.jetbrains.compose.resources.painterResource

@Composable
fun FMMessageInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val canSend = enabled && value.isNotBlank()

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 6.dp,
                top = 6.dp,
                bottom = 6.dp
            ),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp),
                enabled = enabled,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                cursorBrush = SolidColor(
                    MaterialTheme.colorScheme.primary
                ),
                minLines = 1,
                maxLines = 6,
                decorationBox = { innerTextField ->
                    Box {
                        if (value.isEmpty()) {
                            FMText(
                                text = "Message",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        innerTextField()
                    }
                }
            )

            FilledIconButton(
                onClick = onSendClick,
                enabled = canSend,
                modifier = Modifier.size(44.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_send),
                    contentDescription = "Send message"
                )
            }
        }
    }
}

@Preview(name = "Message Input - Light")
@Composable
private fun FMMessageInputLightPreview() {
    FamilyMessengerTheme(darkTheme = false) {
        FMMessageInputPreviewContent()
    }
}

@Preview(name = "Message Input - Dark")
@Composable
private fun FMMessageInputDarkPreview() {
    FamilyMessengerTheme(darkTheme = true) {
        FMMessageInputPreviewContent()
    }
}

@Composable
private fun FMMessageInputPreviewContent() {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FMMessageInput(
                value = "",
                onValueChange = {},
                onSendClick = {}
            )

            FMMessageInput(
                value = "Hello! 👋",
                onValueChange = {},
                onSendClick = {}
            )

            FMMessageInput(
                value = """
                    Hey! How are you?
                    Are we still meeting this weekend?
                    I was thinking we could have dinner together.
                """.trimIndent(),
                onValueChange = {},
                onSendClick = {}
            )
        }
    }
}
