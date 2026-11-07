
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
        cursor: String? = null,
    ): MessagePage {
        require(limit in 1..100)

        val decodedCursor = cursor?.let(MessageCursor::decode)

        val sql = buildString {
            append(
                """
            SELECT id, chat_id, sender_id, content, created_at
            FROM messages
            WHERE chat_id = ?
            """.trimIndent()
            )

            if (decodedCursor != null) {
                append(
                    """
                
                AND (created_at, id) < (?, ?)
                """.trimIndent()
                )
            }

            append(
                """
            
            ORDER BY created_at DESC, id DESC
            LIMIT ?
            """.trimIndent()
            )
        }

        val args = mutableListOf<Any>(chatId)

        if (decodedCursor != null) {
            args.add(decodedCursor.createdAt)
            args.add(decodedCursor.id)
        }

        args.add(limit + 1)

        val messages = jdbcTemplate.query(
            sql,
            { rs, _ -> rs.toMessage() },
            *args.toTypedArray(),
        )

        val hasMore = messages.size > limit
        val items = messages.take(limit)

        val nextCursor = if (hasMore) {
            items.last().let {
                MessageCursor(it.createdAt, it.id).encode()
            }
        } else {
            null
        }

        return MessagePage(
            items = items,
            nextCursor = nextCursor,
        )
    }

    private fun ResultSet.toMessage(): Message =
        Message(
            id = getObject("id", UUID::class.java),
            chatId = getObject("chat_id", UUID::class.java),
            senderId = getObject("sender_id", UUID::class.java),
            content = getString("content"),
            createdAt = getObject("created_at", OffsetDateTime::class.java),
        )
}
