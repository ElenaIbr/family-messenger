
package com.elenaibraeva.familymessenger.backend.message

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.time.OffsetDateTime
import java.util.UUID

@Repository
class MessageRepository(
    private val jdbcTemplate: JdbcTemplate,
) {

    fun save(
        chatId: UUID,
        senderId: UUID,
        content: String,
    ): Message {
        val id = UUID.randomUUID()

        return jdbcTemplate.queryForObject(
            """
            INSERT INTO messages (id, chat_id, sender_id, content)
            VALUES (?, ?, ?, ?)
            RETURNING id, chat_id, sender_id, content, created_at
            """.trimIndent(),
            { rs, _ -> rs.toMessage() },
            id,
            chatId,
            senderId,
            content,
        ) ?: error("Failed to save message")
    }

    fun findByChatId(
        chatId: UUID,
        limit: Int = 50,
    ): List<Message> =
        jdbcTemplate.query(
            """
            SELECT id, chat_id, sender_id, content, created_at
            FROM messages
            WHERE chat_id = ?
            ORDER BY created_at DESC, id DESC
            LIMIT ?
            """.trimIndent(),
            { rs, _ -> rs.toMessage() },
            chatId,
            limit,
        )

    private fun ResultSet.toMessage(): Message =
        Message(
            id = getObject("id", UUID::class.java),
            chatId = getObject("chat_id", UUID::class.java),
            senderId = getObject("sender_id", UUID::class.java),
            content = getString("content"),
            createdAt = getObject("created_at", OffsetDateTime::class.java),
        )
}
