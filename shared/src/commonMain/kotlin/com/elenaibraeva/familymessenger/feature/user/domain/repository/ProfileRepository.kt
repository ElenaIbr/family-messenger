package com.elenaibraeva.familymessenger.feature.user.domain.repository

import com.elenaibraeva.familymessenger.feature.user.data.model.CreateUserRequest
import com.elenaibraeva.familymessenger.feature.user.data.model.UserResponse

interface ProfileRepository {
    suspend fun getMe(): UserResponse?
    suspend fun createMe(request: CreateUserRequest): UserResponse
}
