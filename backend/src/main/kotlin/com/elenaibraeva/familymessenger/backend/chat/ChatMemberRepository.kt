
package com.elenaibraeva.familymessenger.backend.chat

import java.util.UUID
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class ChatMemberRepository(private val jdbcTemplate: JdbcTemplate) {
    fun isMember(chatId: UUID, userId: UUID): Boolean {
        val count = jdbcTemplate.queryForObject(
            """
            SELECT COUNT(*)
            FROM chat_members
            WHERE chat_id = ? AND user_id = ?
            """.trimIndent(),
            Int::class.java,
            chatId,
            userId
        )

        return count == 1
    }
}
