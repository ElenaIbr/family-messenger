package com.elenaibraeva.familymessenger.feature.auth.data.repository

import com.elenaibraeva.familymessenger.feature.auth.data.mapper.toAuthException
import com.elenaibraeva.familymessenger.feature.auth.data.model.AuthState
import com.elenaibraeva.familymessenger.feature.auth.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class SupabaseAuthRepository(private val supabaseClient: SupabaseClient) : AuthRepository {

    override val authState: Flow<AuthState> =
        supabaseClient.auth.sessionStatus
            .map { status: SessionStatus ->
                when (status) {
                    SessionStatus.Initializing ->
                        AuthState.Initializing

                    is SessionStatus.NotAuthenticated ->
                        AuthState.Unauthenticated

                    is SessionStatus.Authenticated -> {
                        status.session.accessToken
                        val userId = status.session.user?.id

                        if (userId != null) {
                            AuthState.Authenticated(userId)
                        } else {
                            AuthState.SessionUnavailable
                        }
                    }

                    is SessionStatus.RefreshFailure ->
                        AuthState.SessionUnavailable
                }
            }
            .distinctUntilChanged()

    override suspend fun requestOtp(email: String) = executeAuthRequest {
        supabaseClient.auth.signInWith(OTP) {
            this.email = email
            createUser = true
        }
    }

    override suspend fun verifyOtp(email: String, code: String) = executeAuthRequest {
        supabaseClient.auth.verifyEmailOtp(
            type = OtpType.Email.EMAIL,
            email = email,
            token = code
        )
    }

    override suspend fun signOut() = executeAuthRequest {
        supabaseClient.auth.signOut()
    }

    private suspend fun executeAuthRequest(block: suspend () -> Unit) {
        try {
            block()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw e.toAuthException()
        }
    }
}
