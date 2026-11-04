package com.elenaibraeva.familymessenger.backend.message

import java.time.OffsetDateTime
import java.util.UUID

data class Message(
    val id: UUID,
    val chatId: UUID,
    val senderId: UUID,
    val content: String,
    val createdAt: OffsetDateTime,
)
