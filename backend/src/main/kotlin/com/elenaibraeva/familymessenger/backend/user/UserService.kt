package com.elenaibraeva.familymessenger.backend.user

import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun create(
        id: UUID,
        request: CreateUserRequest,
    ): User {
        val firstName = request.firstName.trim()

        require(firstName.isNotEmpty()) {
            "First name cannot be empty"
        }

        require(userRepository.findById(id) == null) {
            "User already exists"
        }

        val now = Instant.now()

        val user = User(
            id = id,
            firstName = firstName,
            lastName = request.lastName
                ?.trim()
                ?.takeIf { it.isNotEmpty() },
            dateOfBirth = request.dateOfBirth,
            avatarUrl = null,
            showBirthday = false,
            createdAt = now,
            updatedAt = now,
        )

        return userRepository.create(user)
    }

    fun findById(id: UUID): User? =
        userRepository.findById(id)
}
