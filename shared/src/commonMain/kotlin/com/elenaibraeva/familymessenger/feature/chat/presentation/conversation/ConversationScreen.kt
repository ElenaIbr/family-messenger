
package com.elenaibraeva.familymessenger.feature.chat.presentation.conversation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.core.designsystem.component.FMMessageInput
import com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme
import com.elenaibraeva.familymessenger.feature.chat.domain.model.MessageContent
import com.elenaibraeva.familymessenger.feature.chat.presentation.component.FMMessageBubble
import com.elenaibraeva.familymessenger.feature.chat.presentation.model.MessageDirection
import com.elenaibraeva.familymessenger.feature.chat.presentation.model.MessageUiModel
import com.elenaibraeva.familymessenger.generated.resources.Res
import com.elenaibraeva.familymessenger.generated.resources.ic_arrow_back
import org.jetbrains.compose.resources.painterResource

@Composable
fun ConversationScreen(
    title: String,
    messages: List<MessageUiModel>,
    isLoading: Boolean,
    isSending: Boolean,
    onBackClick: () -> Unit,
    onSendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var messageInput by rememberSaveable {
        mutableStateOf("")
    }

    // Backend returns newest messages first.
    // The UI displays them in chronological order.
    val chronologicalMessages = remember(messages) {
        messages.reversed()
    }

    val listState = rememberLazyListState()

    val latestMessageId = chronologicalMessages.lastOrNull()?.id

    var previousLatestMessageId by remember {
        mutableStateOf<String?>(null)
    }

    var previousMessageCount by remember {
        mutableStateOf(0)
    }

    // Scroll to the bottom on first load.
    // For new messages, only scroll when already near the bottom.
    LaunchedEffect(latestMessageId) {
        if (latestMessageId == null) {
            return@LaunchedEffect
        }

        val lastIndex = chronologicalMessages.lastIndex

        val isInitialLoad = previousLatestMessageId == null

        val lastVisibleIndex = listState.layoutInfo
            .visibleItemsInfo
            .lastOrNull()
            ?.index ?: 0

        val wasNearBottom = lastVisibleIndex >=
            (previousMessageCount - 2).coerceAtLeast(0)

        if (isInitialLoad) {
            listState.scrollToItem(lastIndex)
        } else if (wasNearBottom) {
            listState.animateScrollToItem(lastIndex)
        }

        previousLatestMessageId = latestMessageId
        previousMessageCount = chronologicalMessages.size
    }

    Scaffold(
        modifier = modifier
            .navigationBarsPadding()
            .imePadding(),
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                painter = painterResource(
                                    Res.drawable.ic_arrow_back
                                ),
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        },
        bottomBar = {
            FMMessageInput(
                value = messageInput,
                onValueChange = { messageInput = it },
                onSendClick = {
                    val content = messageInput.trim()

                    if (
                        content.isNotEmpty() &&
                        content.length <= 4000 &&
                        !isSending
                    ) {
                        onSendMessage(content)
                        messageInput = ""
                    }
                },
                enabled = !isSending,
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
            )
        }
    ) { padding ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            messages.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No messages yet",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            else -> {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 8.dp,
                        alignment = Alignment.Bottom
                    )
                ) {
                    items(
                        items = chronologicalMessages,
                        key = { it.id }
                    ) { message ->
                        FMMessageBubble(
                            message = message,
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ConversationScreenPreview() {
    FamilyMessengerTheme {
        ConversationScreen(
            title = "Bob",
            messages = listOf(
                MessageUiModel(
                    id = "3",
                    content = MessageContent.Text(
                        "Hello from Family Messenger!"
                    ),
                    direction = MessageDirection.OUTGOING,
                    formattedTime = "14:47"
                ),
                MessageUiModel(
                    id = "2",
                    content = MessageContent.Text("Hi Alice!"),
                    direction = MessageDirection.INCOMING,
                    formattedTime = "14:45"
                ),
                MessageUiModel(
                    id = "1",
                    content = MessageContent.Text("Hello Bob!"),
                    direction = MessageDirection.OUTGOING,
                    formattedTime = "14:44"
                )
            ),
            isLoading = false,
            isSending = false,
            onBackClick = {},
            onSendMessage = {}
        )
    }
}
