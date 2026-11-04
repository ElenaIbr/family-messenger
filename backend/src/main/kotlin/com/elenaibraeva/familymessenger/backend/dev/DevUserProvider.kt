package com.elenaibraeva.familymessenger.backend.dev

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.UUID

@Component
@Profile("dev")
class DevUserProvider {

    val userId: UUID = UUID.fromString(
        "11111111-1111-1111-1111-111111111111"
    )
}
