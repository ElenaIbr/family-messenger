package com.elenaibraeva.familymessenger.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

private val navigationSavedStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(
                Destination.Auth.EmailEntry::class,
                Destination.Auth.EmailEntry.serializer(),
            )
            subclass(
                Destination.Auth.OtpVerification::class,
                Destination.Auth.OtpVerification.serializer(),
            )
            subclass(
                Destination.Auth.ProfileSetup::class,
                Destination.Auth.ProfileSetup.serializer(),
            )

            subclass(
                Destination.Main.Chats::class,
                Destination.Main.Chats.serializer(),
            )
            subclass(
                Destination.Main.Conversation::class,
                Destination.Main.Conversation.serializer(),
            )
            subclass(
                Destination.Main.NewChat::class,
                Destination.Main.NewChat.serializer(),
            )
            subclass(
                Destination.Main.Profile::class,
                Destination.Main.Profile.serializer(),
            )
        }
    }
}

@Composable
fun AppNavHost() {

    val backStack = rememberNavBackStack(
        configuration = navigationSavedStateConfiguration,
        Destination.Main.Chats,
    )

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Destination.Main.Chats> {
                Column {
                    Text("Chats")

                    Button(
                        onClick = {
                            backStack.add(Destination.Main.Profile)
                        },
                    ) {
                        Text("Open Profile")
                    }
                }
            }

            entry<Destination.Main.Profile> {
                Text("Profile")
            }
        },
    )
}
