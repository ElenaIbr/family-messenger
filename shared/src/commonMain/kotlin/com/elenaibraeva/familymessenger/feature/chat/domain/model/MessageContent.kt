package com.elenaibraeva.familymessenger.feature.chat.domain.model

sealed interface MessageContent {
    data class Text(val value: String) : MessageContent
}
