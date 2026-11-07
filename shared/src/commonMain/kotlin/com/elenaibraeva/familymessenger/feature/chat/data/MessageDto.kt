package com.elenaibraeva.familymessenger.feature.chat.data

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id: String,
    val chatId: String,
    val senderId: String,
    val content: String,
    val createdAt: String,
)

@Serializable
data class MessagePageDto(
    val items: List<MessageDto>,
    val nextCursor: String?,
)

@Serializable
data class SendMessageRequest(
    val content: String,
)

