package com.elenaibraeva.familymessenger.backend.message

data class MessagePage(val items: List<Message>, val nextCursor: String?)
