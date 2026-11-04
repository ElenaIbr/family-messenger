package com.elenaibraeva.familymessenger.backend

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.postgresql.PostgreSQLContainer
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.UUID

@Testcontainers
@SpringBootTest(
    properties = ["spring.docker.compose.enabled=false"]
)
class DatabaseIntegrationTest {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun `should persist and retrieve a message`() {
        val aliceId = UUID.randomUUID()
        val bobId = UUID.randomUUID()
        val chatId = UUID.randomUUID()
        val messageId = UUID.randomUUID()

        jdbcTemplate.update(
            "INSERT INTO users (id, display_name) VALUES (?, ?)",
            aliceId, "Alice"
        )
        jdbcTemplate.update(
            "INSERT INTO users (id, display_name) VALUES (?, ?)",
            bobId, "Bob"
        )
        jdbcTemplate.update(
            "INSERT INTO chats (id, type) VALUES (?, ?)",
            chatId, "DIRECT"
        )
        jdbcTemplate.update(
            "INSERT INTO chat_members (chat_id, user_id) VALUES (?, ?), (?, ?)",
            chatId, aliceId, chatId, bobId
        )
        jdbcTemplate.update(
            "INSERT INTO messages (id, chat_id, sender_id, content) VALUES (?, ?, ?, ?)",
            messageId, chatId, aliceId, "Hello, Bob!"
        )

        val content = jdbcTemplate.queryForObject(
            "SELECT content FROM messages WHERE id = ?",
            String::class.java,
            messageId
        )

        assertEquals("Hello, Bob!", content)
    }

    @Test
    fun `should reject duplicate chat membership`() {
        val userId = UUID.randomUUID()
        val chatId = UUID.randomUUID()

        jdbcTemplate.update(
            "INSERT INTO users (id, display_name) VALUES (?, ?)",
            userId, "Alice"
        )
        jdbcTemplate.update(
            "INSERT INTO chats (id, type) VALUES (?, ?)",
            chatId, "GROUP"
        )
        jdbcTemplate.update(
            "INSERT INTO chat_members (chat_id, user_id) VALUES (?, ?)",
            chatId, userId
        )

        assertThrows(Exception::class.java) {
            jdbcTemplate.update(
                "INSERT INTO chat_members (chat_id, user_id) VALUES (?, ?)",
                chatId, userId
            )
        }
    }

    @Test
    fun `should reject blank messages`() {
        val userId = UUID.randomUUID()
        val chatId = UUID.randomUUID()

        jdbcTemplate.update(
            "INSERT INTO users (id, display_name) VALUES (?, ?)",
            userId, "Alice"
        )
        jdbcTemplate.update(
            "INSERT INTO chats (id, type) VALUES (?, ?)",
            chatId, "DIRECT"
        )
        jdbcTemplate.update(
            "INSERT INTO chat_members (chat_id, user_id) VALUES (?, ?)",
            chatId, userId
        )

        assertThrows(Exception::class.java) {
            jdbcTemplate.update(
                "INSERT INTO messages (id, chat_id, sender_id, content) VALUES (?, ?, ?, ?)",
                UUID.randomUUID(), chatId, userId, "   "
            )
        }
    }

    companion object {
        @Container
        @JvmStatic
        val postgres = PostgreSQLContainer("postgres:17")
            .withDatabaseName("family_messenger_test")
            .withUsername("test_user")
            .withPassword("test_password")

        @JvmStatic
        @DynamicPropertySource
        fun configureDatabase(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }
    }
}
