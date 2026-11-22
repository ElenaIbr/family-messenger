package com.elenaibraeva.familymessenger.feature.auth.data

import com.elenaibraeva.familymessenger.feature.auth.domain.AccessTokenProvider
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth

class SupabaseAccessTokenProvider(
    private val supabaseClient: SupabaseClient,
) : AccessTokenProvider {

    override suspend fun getAccessToken(): String {
        val session = supabaseClient.auth.currentSessionOrNull()
            ?: error("No active Supabase session")

        return session.accessToken
    }
}
