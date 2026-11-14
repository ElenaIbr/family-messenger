package com.elenaibraeva.familymessenger.core.security

interface SecureStorage {

    suspend fun write(key: String, value: String)

    suspend fun read(key: String): String?

    suspend fun delete(key: String)
}
