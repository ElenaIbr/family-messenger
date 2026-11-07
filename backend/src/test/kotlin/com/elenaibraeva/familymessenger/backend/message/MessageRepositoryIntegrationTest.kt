
package com.elenaibraeva.familymessenger.backend.message

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import java.util.UUID

@Testcontainers
@SpringBootTest(properties = ["spring.docker.compose.enabled=false"])
class MessageRepositoryIntegrationTest {

    @Autowired
    lateinit var repository: MessageRepository

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun `should save and retrieve a message`() {
        val aliceId = UUID.randomUUID()
        val bobId = UUID.randomUUID()
        val chatId = UUID.randomUUID()

        jdbcTemplate.update(
            "INSERT INTO users (id, display_name) VALUES (?, ?), (?, ?)",
            aliceId, "Alice", bobId, "Bob",
        )

        jdbcTemplate.update(
            "INSERT INTO chats (id, type) VALUES (?, ?)",
            chatId, "DIRECT",
        )

        jdbcTemplate.update(
            "INSERT INTO chat_members (chat_id, user_id) VALUES (?, ?), (?, ?)",
            chatId, aliceId, chatId, bobId,
        )

        val saved = repository.save(
            chatId = chatId,
            senderId = aliceId,
            content = "Hello, Bob!",
        )

        assertNotNull(saved.id)
        assertEquals(chatId, saved.chatId)
        assertEquals(aliceId, saved.senderId)
        assertEquals("Hello, Bob!", saved.content)
        assertNotNull(saved.createdAt)

        val page = repository.findByChatId(chatId)

        assertEquals(1, page.items.size)
        assertEquals(saved, page.items.single())
        assertEquals(null, page.nextCursor)
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


    @Test
    fun `should paginate messages using cursor`() {
        val aliceId = UUID.randomUUID()
        val bobId = UUID.randomUUID()
        val chatId = UUID.randomUUID()

        jdbcTemplate.update(
            "INSERT INTO users (id, display_name) VALUES (?, ?), (?, ?)",
            aliceId, "Alice", bobId, "Bob",
        )

        jdbcTemplate.update(
            "INSERT INTO chats (id, type) VALUES (?, ?)",
            chatId, "DIRECT",
        )

        jdbcTemplate.update(
            "INSERT INTO chat_members (chat_id, user_id) VALUES (?, ?), (?, ?)",
            chatId, aliceId, chatId, bobId,
        )

        repeat(5) { index ->
            repository.save(
                chatId = chatId,
                senderId = aliceId,
                content = "Message $index",
            )
        }

        val firstPage = repository.findByChatId(
            chatId = chatId,
            limit = 2,
        )

        assertEquals(2, firstPage.items.size)
        assertNotNull(firstPage.nextCursor)

        val secondPage = repository.findByChatId(
            chatId = chatId,
            limit = 2,
            cursor = firstPage.nextCursor,
        )

        assertEquals(2, secondPage.items.size)
        assertNotNull(secondPage.nextCursor)

        val thirdPage = repository.findByChatId(
            chatId = chatId,
            limit = 2,
            cursor = secondPage.nextCursor,
        )

        assertEquals(1, thirdPage.items.size)
        assertEquals(null, thirdPage.nextCursor)

        val allIds = (
                firstPage.items +
                        secondPage.items +
                        thirdPage.items
                ).map { it.id }

        assertEquals(5, allIds.distinct().size)
    }
}
