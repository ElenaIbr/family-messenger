package com.elenaibraeva.familymessenger.feature.auth.domain

interface AccessTokenProvider {
    suspend fun getAccessToken(): String
}
