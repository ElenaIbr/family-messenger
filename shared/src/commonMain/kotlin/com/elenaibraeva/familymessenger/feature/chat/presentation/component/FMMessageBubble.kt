package com.elenaibraeva.familymessenger.feature.chat.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.core.designsystem.component.FMText
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyTheme
import com.elenaibraeva.familymessenger.feature.chat.domain.model.MessageContent
import com.elenaibraeva.familymessenger.feature.chat.domain.model.MessageDeliveryStatus
import com.elenaibraeva.familymessenger.feature.chat.presentation.model.MessageDirection
import com.elenaibraeva.familymessenger.feature.chat.presentation.model.MessageUiModel

@Composable
fun FMMessageBubble(message: MessageUiModel, modifier: Modifier = Modifier) {
    val isOutgoing = message.direction == MessageDirection.OUTGOING
    val colors = FamilyTheme.colors

    val backgroundColor = if (isOutgoing) {
        colors.outgoingMessageBackground
    } else {
        colors.incomingMessageBackground
    }

    val contentColor = if (isOutgoing) {
        colors.outgoingMessageText
    } else {
        colors.incomingMessageText
    }

    val shape = if (isOutgoing) {
        RoundedCornerShape(
            topStart = 18.dp,
            topEnd = 18.dp,
            bottomStart = 18.dp,
            bottomEnd = 4.dp
        )
    } else {
        RoundedCornerShape(
            topStart = 18.dp,
            topEnd = 18.dp,
            bottomStart = 4.dp,
            bottomEnd = 18.dp
        )
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = if (isOutgoing) {
            Arrangement.End
        } else {
            Arrangement.Start
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .widthIn(max = 360.dp)
                .clip(shape)
                .background(backgroundColor)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            when (val content = message.content) {
                is MessageContent.Text -> {
                    TextMessageContent(
                        text = content.value,
                        color = contentColor
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            Row(
                modifier = Modifier.align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                FMText(
                    text = message.formattedTime,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isOutgoing) {
                        colors.outgoingMessageText
                    } else {
                        colors.incomingMessageText
                    }
                )

                if (isOutgoing) {
                    message.deliveryStatus?.let { status ->
                        MessageStatusIcon(status = status)
                    }
                }
            }
        }
    }
}

@Preview(name = "Message Bubbles - Light", showBackground = true)
@Composable
private fun FMMessageBubbleLightPreview() {
    FamilyMessengerTheme(darkTheme = false) {
        MessageBubblesPreviewContent()
    }
}

@Preview(name = "Message Bubbles - Dark", showBackground = true)
@Composable
private fun FMMessageBubbleDarkPreview() {
    FamilyMessengerTheme(darkTheme = true) {
        MessageBubblesPreviewContent()
    }
}

@Composable
private fun MessageBubblesPreviewContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FMMessageBubble(
            message = MessageUiModel(
                id = "1",
                content = MessageContent.Text("Hey! 😊"),
                direction = MessageDirection.INCOMING,
                formattedTime = "12:41"
            )
        )

        FMMessageBubble(
            message = MessageUiModel(
                id = "2",
                content = MessageContent.Text("Hi! How are you?"),
                direction = MessageDirection.OUTGOING,
                formattedTime = "12:42",
                deliveryStatus = MessageDeliveryStatus.READ
            )
        )

        FMMessageBubble(
            message = MessageUiModel(
                id = "3",
                content = MessageContent.Text(
                    "I'm doing well! Are we still meeting this weekend? " +
                        "I was thinking Saturday afternoon would be perfect."
                ),
                direction = MessageDirection.INCOMING,
                formattedTime = "12:43"
            )
        )

        FMMessageBubble(
            message = MessageUiModel(
                id = "4",
                content = MessageContent.Text("Saturday works for me! 💜"),
                direction = MessageDirection.OUTGOING,
                formattedTime = "12:44",
                deliveryStatus = MessageDeliveryStatus.DELIVERED
            )
        )

        FMMessageBubble(
            message = MessageUiModel(
                id = "5",
                content = MessageContent.Text("See you there!"),
                direction = MessageDirection.OUTGOING,
                formattedTime = "12:45",
                deliveryStatus = MessageDeliveryStatus.SENDING
            )
        )

        FMMessageBubble(
            message = MessageUiModel(
                id = "6",
                content = MessageContent.Text("One more thing..."),
                direction = MessageDirection.OUTGOING,
                formattedTime = "12:46",
                deliveryStatus = MessageDeliveryStatus.FAILED
            )
        )
    }
}
