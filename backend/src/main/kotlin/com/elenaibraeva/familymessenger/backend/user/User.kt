package com.elenaibraeva.familymessenger.backend.user

import java.time.Instant
import java.time.LocalDate
import java.util.UUID

data class User(
    val id: UUID,
    val firstName: String,
    val lastName: String?,
    val dateOfBirth: LocalDate?,
    val avatarUrl: String?,
    val showBirthday: Boolean = false,
    val createdAt: Instant,
    val updatedAt: Instant,
)
