package com.elenaibraeva.familymessenger.backend.user

import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/me")
class UserController(
    private val userService: UserService,
) {

    @GetMapping
    fun getMe(
        @AuthenticationPrincipal jwt: Jwt,
    ): UserResponse {
        val userId = UUID.fromString(jwt.subject)

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
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody request: CreateUserRequest,
    ): UserResponse {
        val userId = UUID.fromString(jwt.subject)

        return userService.create(
            id = userId,
            request = request,
        ).toResponse()
    }
}
