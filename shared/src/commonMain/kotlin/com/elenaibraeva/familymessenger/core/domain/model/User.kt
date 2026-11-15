package com.elenaibraeva.familymessenger.core.domain.model

data class User(
    val id: String,
    val displayName: String,
    val avatarUrl: String? = null
)
