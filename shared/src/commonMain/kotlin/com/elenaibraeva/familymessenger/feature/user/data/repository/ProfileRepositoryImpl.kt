package com.elenaibraeva.familymessenger.feature.user.data.repository

import com.elenaibraeva.familymessenger.feature.user.data.model.CreateUserRequest
import com.elenaibraeva.familymessenger.feature.user.data.model.UserResponse
import com.elenaibraeva.familymessenger.feature.user.data.remote.UserApi
import com.elenaibraeva.familymessenger.feature.user.domain.repository.ProfileRepository
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode

class ProfileRepositoryImpl(
    private val api: UserApi,
) : ProfileRepository {

    override suspend fun getMe(): UserResponse? =
        try {
            api.getMe()
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.NotFound) {
                null
            } else {
                throw e
            }
        }

    override suspend fun createMe(request: CreateUserRequest): UserResponse =
        api.createMe(request)
}
