package com.elenaibraeva.familymessenger.feature.chatlist.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elenaibraeva.familymessenger.feature.chatlist.presentation.component.ChatRow
import com.elenaibraeva.familymessenger.feature.chatlist.presentation.component.ChatSearchBar
import com.elenaibraeva.familymessenger.feature.chatlist.presentation.component.CreateChatFab
import com.elenaibraeva.familymessenger.feature.chatlist.presentation.model.ChatRowUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(
    chats: List<ChatRowUiModel>,
    onChatClick: (String) -> Unit,
    onCreateChatClick: () -> Unit,
    onDeleteChat: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredChats = remember(chats, searchQuery) {
        chats.filter { chat ->
            chat.title.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Family Messenger") }
            )
        },
        floatingActionButton = {
            CreateChatFab(onClick = onCreateChatClick)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ChatSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            if (filteredChats.isEmpty()) {
                Text(
                    text = if (searchQuery.isBlank()) {
                        "No chats yet"
                    } else {
                        "No matching chats"
                    },
                    modifier = Modifier.padding(24.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 88.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    items(
                        items = filteredChats,
                        key = { it.id }
                    ) { chat ->
                        ChatRow(
                            chat = chat,
                            onClick = { onChatClick(chat.id) },
                            onDelete = { onDeleteChat(chat.id) }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChatsScreenPreview() {
    com.elenaibraeva.familymessenger.core.designsystem.theme.FamilyMessengerTheme {
        ChatsScreen(
            chats = listOf(
                ChatRowUiModel(
                    id = "33333333-3333-3333-3333-333333333333",
                    title = "Bob",
                    avatarUrl = null,
                    avatarInitials = "B",
                    lastMessage = "Hello from Family Messenger!",
                    formattedTime = "14:47",
                    unreadCount = 0
                ),
                ChatRowUiModel(
                    id = "preview-family",
                    title = "Family group",
                    avatarUrl = null,
                    avatarInitials = "FG",
                    lastMessage = "No messages yet",
                    formattedTime = "",
                    unreadCount = 0
                )
            ),
            onChatClick = {},
            onCreateChatClick = {},
            onDeleteChat = {}
        )
    }
}
