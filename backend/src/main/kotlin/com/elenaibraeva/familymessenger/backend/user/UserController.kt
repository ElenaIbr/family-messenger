package com.elenaibraeva.familymessenger.backend.user

import com.elenaibraeva.familymessenger.backend.dev.DevUserProvider
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@RestController
@Profile("dev")
@RequestMapping("/api/v1/me")
class UserController(
    private val userService: UserService,
    private val devUserProvider: DevUserProvider,
) {

    @GetMapping
    fun getMe(
        @RequestHeader("X-Dev-User-Id", required = false)
        devUserId: UUID?,
    ): UserResponse {
        val userId = devUserId ?: devUserProvider.userId

        return userService.findById(userId)
            ?.toResponse()
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User profile not found",
            )
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createMe(
        @RequestHeader("X-Dev-User-Id", required = false)
        devUserId: UUID?,
        @RequestBody request: CreateUserRequest,
    ): UserResponse {
        val userId = devUserId ?: devUserProvider.userId

        return userService.create(
            id = userId,
            request = request,
        ).toResponse()
    }
}
