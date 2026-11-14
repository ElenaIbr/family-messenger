package com.elenaibraeva.familymessenger.core.auth

import com.elenaibraeva.familymessenger.core.security.SecureStorage
import io.github.jan.supabase.auth.SessionManager
import io.github.jan.supabase.auth.exception.NoSessionFoundException
import io.github.jan.supabase.auth.user.UserSession
import kotlinx.serialization.json.Json

class SupabaseSessionManager(private val secureStorage: SecureStorage) : SessionManager {

    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    override suspend fun saveSession(session: UserSession) {
        secureStorage.write(
            key = SESSION_KEY,
            value = json.encodeToString(session)
        )
    }

    override suspend fun loadSession(): UserSession {
        val storedSession = secureStorage.read(SESSION_KEY)
            ?: throw NoSessionFoundException()

        return json.decodeFromString<UserSession>(storedSession)
    }

    override suspend fun deleteSession() {
        secureStorage.delete(SESSION_KEY)
    }

    private companion object {
        const val SESSION_KEY = "supabase_session"
    }
}
