package com.elenaibraeva.familymessenger.backend.user

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.UUID

@Repository
class UserRepository(private val jdbcClient: JdbcClient) {

    fun findById(id: UUID): User? {
        return jdbcClient.sql(
            """
        SELECT *
        FROM users
        WHERE id = :id
        """.trimIndent()
        )
            .param("id", id)
            .query(userRowMapper)
            .optional()
            .orElse(null)
    }

    fun create(user: User): User {
        val displayName = listOfNotNull(
            user.firstName.trim(),
            user.lastName?.trim()?.takeIf { it.isNotEmpty() },
        ).joinToString(" ")

        return jdbcClient.sql(
        """
        INSERT INTO users (
            id,
            display_name,
            first_name,
            last_name,
            date_of_birth,
            avatar_url,
            show_birthday
        )
        VALUES (
            :id,
            :displayName,
            :firstName,
            :lastName,
            :dateOfBirth,
            :avatarUrl,
            :showBirthday
        )
        RETURNING *
        """.trimIndent()
        )
            .param("id", user.id)
            .param("displayName", displayName)
            .param("firstName", user.firstName.trim())
            .param("lastName", user.lastName?.trim()?.takeIf { it.isNotEmpty() })
            .param("dateOfBirth", user.dateOfBirth)
            .param("avatarUrl", user.avatarUrl)
            .param("showBirthday", user.showBirthday)
            .query(userRowMapper)
            .single()
    }

    private val userRowMapper = RowMapper { rs, _ ->
        User(
            id = rs.getObject("id", UUID::class.java),
            firstName = rs.getString("first_name"),
            lastName = rs.getString("last_name"),
            dateOfBirth = rs.getObject("date_of_birth", LocalDate::class.java),
            avatarUrl = rs.getString("avatar_url"),
            showBirthday = rs.getBoolean("show_birthday"),
            createdAt = rs.getTimestamp("created_at").toInstant(),
            updatedAt = rs.getTimestamp("updated_at").toInstant(),
        )
    }
}
