package com.elenaibraeva.familymessenger.feature.auth.domain.repository

import com.elenaibraeva.familymessenger.feature.auth.data.model.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val authState: Flow<AuthState>

    suspend fun requestOtp(email: String)

    suspend fun verifyOtp(email: String, code: String)

    suspend fun signOut()
}
