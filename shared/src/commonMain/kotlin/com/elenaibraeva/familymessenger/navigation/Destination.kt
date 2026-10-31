package com.elenaibraeva.familymessenger.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Destination : NavKey {
    sealed interface Auth : Destination {
        @Serializable
        data object EmailEntry : Auth

        @Serializable
        data class OtpVerification(val email: String) : Auth

        @Serializable
        data object ProfileSetup : Auth
    }

    sealed interface Main : Destination {
        @Serializable
        data object Chats : Main

        @Serializable
        data class Conversation(val chatId: String) : Main

        @Serializable
        data object NewChat : Main

        @Serializable
        data object Profile : Main
    }
}
