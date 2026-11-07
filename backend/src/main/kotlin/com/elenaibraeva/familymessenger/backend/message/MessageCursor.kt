
package com.elenaibraeva.familymessenger.backend.message

import java.nio.charset.StandardCharsets
import java.time.OffsetDateTime
import java.util.Base64
import java.util.UUID

data class MessageCursor(val createdAt: OffsetDateTime, val id: UUID) {
    fun encode(): String {
        val value = "$createdAt|$id"

        return Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(
                value.toByteArray(StandardCharsets.UTF_8)
            )
    }

    companion object {
        fun decode(cursor: String): MessageCursor {
            try {
                val decoded = String(
                    Base64.getUrlDecoder().decode(cursor),
                    StandardCharsets.UTF_8
                )

                val parts = decoded.split("|", limit = 2)
                require(parts.size == 2)

                return MessageCursor(
                    createdAt = OffsetDateTime.parse(parts[0]),
                    id = UUID.fromString(parts[1])
                )
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException(
                    "Invalid message cursor",
                    e
                )
            }
        }
    }
}
