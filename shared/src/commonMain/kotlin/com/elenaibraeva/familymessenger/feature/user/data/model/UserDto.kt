package com.elenaibraeva.familymessenger.feature.user.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(
    val firstName: String,
    val lastName: String? = null,
    val dateOfBirth: String? = null,
)

@Serializable
data class UserResponse(
    val id: String,
    val firstName: String,
    val lastName: String?,
    val dateOfBirth: String?,
    val avatarUrl: String?,
    val showBirthday: Boolean,
    val createdAt: String,
    val updatedAt: String,
)
