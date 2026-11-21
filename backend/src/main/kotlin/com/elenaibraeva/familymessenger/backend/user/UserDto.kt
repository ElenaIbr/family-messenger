package com.elenaibraeva.familymessenger.backend.user

import java.time.Instant
import java.time.LocalDate
import java.util.UUID

data class CreateUserRequest(
    val firstName: String,
    val lastName: String?,
    val dateOfBirth: LocalDate?,
)

data class UserResponse(
    val id: UUID,
    val firstName: String,
    val lastName: String?,
    val dateOfBirth: LocalDate?,
    val avatarUrl: String?,
    val showBirthday: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant,
)

fun User.toResponse(): UserResponse =
    UserResponse(
        id = id,
        firstName = firstName,
        lastName = lastName,
        dateOfBirth = dateOfBirth,
        avatarUrl = avatarUrl,
        showBirthday = showBirthday,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

