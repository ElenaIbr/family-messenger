package com.elenaibraeva.familymessenger.feature.chatlist.presentation.model

data class ChatRowUiModel(
    val id: String,
    val title: String,
    val avatarUrl: String?,
    val avatarInitials: String?,
    val lastMessage: String,
    val formattedTime: String,
    val unreadCount: Int = 0
)
