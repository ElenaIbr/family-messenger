package com.elenaibraeva.familymessenger.feature.chat.domain.model

import kotlin.time.Instant

data class Message(
    val id: String,
    val conversationId: String,
    val senderId: String,
    val content: MessageContent,
    val createdAt: Instant,
    val deliveryStatus: MessageDeliveryStatus? = null,
)
