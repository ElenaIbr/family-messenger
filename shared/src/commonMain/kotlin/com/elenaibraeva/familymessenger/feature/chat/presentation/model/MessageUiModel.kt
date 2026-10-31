package com.elenaibraeva.familymessenger.feature.chat.presentation.model

import com.elenaibraeva.familymessenger.feature.chat.domain.model.MessageContent
import com.elenaibraeva.familymessenger.feature.chat.domain.model.MessageDeliveryStatus

data class MessageUiModel(
    val id: String,
    val content: MessageContent,
    val direction: MessageDirection,
    val formattedTime: String,
    val deliveryStatus: MessageDeliveryStatus? = null,
)

