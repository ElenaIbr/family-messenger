package com.elenaibraeva.familymessenger.feature.chatlist.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elenaibraeva.familymessenger.core.designsystem.component.FMAvatar
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme
import com.elenaibraeva.familymessenger.feature.chatlist.presentation.model.ChatRowUiModel
import com.elenaibraeva.familymessenger.generated.resources.Res
import com.elenaibraeva.familymessenger.generated.resources.ic_delete
import kotlin.math.roundToInt
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

private enum class SwipePosition {
    Closed,
    Open
}

private val DeleteActionWidth = 80.dp

@Composable
fun ChatRow(
    chat: ChatRowUiModel,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val deleteWidthPx = with(density) { DeleteActionWidth.toPx() }
    val scope = rememberCoroutineScope()

    val swipeState = remember {
        AnchoredDraggableState(
            initialValue = SwipePosition.Closed
        )
    }

    val anchors = remember(deleteWidthPx) {
        DraggableAnchors {
            SwipePosition.Closed at 0f
            SwipePosition.Open at -deleteWidthPx
        }
    }

    LaunchedEffect(anchors) {
        swipeState.updateAnchors(anchors)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(MaterialTheme.colorScheme.error),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .width(DeleteActionWidth)
                    .heightIn(min = 80.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_delete),
                    contentDescription = "Delete conversation",
                    tint = MaterialTheme.colorScheme.onError
                )
            }
        }

        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = swipeState.offset
                            .takeUnless { it.isNaN() }
                            ?.roundToInt() ?: 0,
                        y = 0
                    )
                }
                .anchoredDraggable(
                    state = swipeState,
                    orientation = Orientation.Horizontal
                )
                .clickable {
                    if (swipeState.currentValue == SwipePosition.Open) {
                        scope.launch {
                            swipeState.animateTo(SwipePosition.Closed)
                        }
                    } else {
                        onClick()
                    }
                }
        ) {
            ChatRowContent(chat = chat)
        }
    }
}

@Composable
private fun ChatRowContent(chat: ChatRowUiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FMAvatar(
            initials = chat.avatarInitials,
            imageUrl = chat.avatarUrl,
            size = 52.dp
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = chat.title,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = chat.formattedTime,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = chat.lastMessage,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (chat.unreadCount > 0) {
                    UnreadBadge(count = chat.unreadCount)
                }
            }
        }
    }
}

@Composable
private fun UnreadBadge(count: Int) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (count > 99) "99+" else count.toString(),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(name = "Chat rows - Light", showBackground = true)
@Composable
private fun ChatRowLightPreview() {
    FamilyMessengerTheme {
        ChatRowPreviewContent()
    }
}

@Preview(name = "Chat rows - Dark", showBackground = true)
@Composable
private fun ChatRowDarkPreview() {
    FamilyMessengerTheme(
        darkTheme = true
    ) {
        ChatRowPreviewContent()
    }
}

@Composable
private fun ChatRowPreviewContent() {
    Column {
        ChatRow(
            chat = ChatRowUiModel(
                id = "1",
                title = "Anna Müller",
                avatarUrl = null,
                avatarInitials = "AM",
                lastMessage = "See you tomorrow! 😊",
                formattedTime = "14:32",
                unreadCount = 3
            ),
            onClick = {},
            onDelete = {}
        )

        ChatRow(
            chat = ChatRowUiModel(
                id = "2",
                title = "Family group",
                avatarUrl = null,
                avatarInitials = "FG",
                lastMessage = "Mom: I sent you the photos from our trip",
                formattedTime = "Yesterday",
                unreadCount = 12
            ),
            onClick = {},
            onDelete = {}
        )

        ChatRow(
            chat = ChatRowUiModel(
                id = "3",
                title = "Alexander",
                avatarUrl = null,
                avatarInitials = "AL",
                lastMessage = "Thanks!",
                formattedTime = "Mon",
                unreadCount = 0
            ),
            onClick = {},
            onDelete = {}
        )

        ChatRow(
            chat = ChatRowUiModel(
                id = "4",
                title = "A very long conversation name that should be truncated",
                avatarUrl = null,
                avatarInitials = "LC",
                lastMessage = "This is a very long message that should be truncated " +
                    "because it does not fit in the conversation list",
                formattedTime = "12:45",
                unreadCount = 105
            ),
            onClick = {},
            onDelete = {}
        )
    }
}
