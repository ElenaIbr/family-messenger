
package com.elenaibraeva.familymessenger.backend.message

import java.time.OffsetDateTime
import java.util.UUID

data class SendMessageRequest(val content: String)

data class MessageResponse(
    val id: UUID,
    val chatId: UUID,
    val senderId: UUID,
    val content: String,
    val createdAt: OffsetDateTime
)

fun Message.toResponse() = MessageResponse(
    id = id,
    chatId = chatId,
    senderId = senderId,
    content = content,
    createdAt = createdAt
)

data class MessagePageResponse(val items: List<MessageResponse>, val nextCursor: String?)

fun MessagePage.toResponse() = MessagePageResponse(
    items = items.map { it.toResponse() },
    nextCursor = nextCursor
)
