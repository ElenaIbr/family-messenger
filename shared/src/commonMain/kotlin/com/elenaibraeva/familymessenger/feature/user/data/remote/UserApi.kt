package com.elenaibraeva.familymessenger.feature.user.data.remote

import com.elenaibraeva.familymessenger.feature.auth.domain.AccessTokenProvider
import com.elenaibraeva.familymessenger.feature.user.data.model.CreateUserRequest
import com.elenaibraeva.familymessenger.feature.user.data.model.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UserApi(
    private val client: HttpClient,
    private val accessTokenProvider: AccessTokenProvider,
) {
    suspend fun getMe(): UserResponse =
        client.get("api/v1/me") {
            bearerAuth(accessTokenProvider.getAccessToken())
        }.body()

    suspend fun createMe(request: CreateUserRequest): UserResponse =
        client.post("api/v1/me") {
            bearerAuth(accessTokenProvider.getAccessToken())
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
}
