package com.elenaibraeva.familymessenger.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.elenaibraeva.familymessenger.feature.chat.domain.model.MessageContent
import com.elenaibraeva.familymessenger.feature.chat.presentation.conversation.ConversationScreen
import com.elenaibraeva.familymessenger.feature.chat.presentation.model.MessageDirection
import com.elenaibraeva.familymessenger.feature.chat.presentation.model.MessageUiModel
import com.elenaibraeva.familymessenger.feature.chatlist.presentation.ChatsScreen
import com.elenaibraeva.familymessenger.feature.chatlist.presentation.model.ChatRowUiModel
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

private val navigationSavedStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(
                Destination.Auth.EmailEntry::class,
                Destination.Auth.EmailEntry.serializer()
            )
            subclass(
                Destination.Auth.OtpVerification::class,
                Destination.Auth.OtpVerification.serializer()
            )
            subclass(
                Destination.Auth.ProfileSetup::class,
                Destination.Auth.ProfileSetup.serializer()
            )
            subclass(
                Destination.Main.Chats::class,
                Destination.Main.Chats.serializer()
            )
            subclass(
                Destination.Main.Conversation::class,
                Destination.Main.Conversation.serializer()
            )
            subclass(
                Destination.Main.NewChat::class,
                Destination.Main.NewChat.serializer()
            )
            subclass(
                Destination.Main.Profile::class,
                Destination.Main.Profile.serializer()
            )
        }
    }
}

private const val DEV_CHAT_ID =
    "33333333-3333-3333-3333-333333333333"

private val previewChats = listOf(
    ChatRowUiModel(
        id = DEV_CHAT_ID,
        title = "Bob",
        avatarUrl = null,
        avatarInitials = "B",
        lastMessage = "Hello from Family Messenger!",
        formattedTime = "14:47",
        unreadCount = 0
    )
)

private val previewMessages = listOf(
    MessageUiModel(
        id = "3",
        content = MessageContent.Text("Hello from Family Messenger!"),
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
)

@Composable
fun AppNavHost() {
    val backStack = rememberNavBackStack(
        configuration = navigationSavedStateConfiguration,
        Destination.Main.Chats
    )

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Destination.Main.Chats> {
                ChatsScreen(
                    chats = previewChats,
                    onChatClick = { chatId ->
                        backStack.add(
                            Destination.Main.Conversation(chatId)
                        )
                    },
                    onCreateChatClick = {
                        backStack.add(Destination.Main.NewChat)
                    },
                    onDeleteChat = {
                        // Not implemented yet.
                    }
                )
            }

            entry<Destination.Main.Conversation> { destination ->
                ConversationScreen(
                    title = "Bob",
                    messages = previewMessages,
                    isLoading = false,
                    isSending = false,
                    onBackClick = {
                        backStack.removeAt(backStack.lastIndex)
                    },
                    onSendMessage = {
                        // Will connect to MessageApi next.
                    }
                )
            }

            entry<Destination.Main.NewChat> {
                androidx.compose.material3.Text("New chat")
            }

            entry<Destination.Main.Profile> {
                androidx.compose.material3.Text("Profile")
            }
        }
    )
}
