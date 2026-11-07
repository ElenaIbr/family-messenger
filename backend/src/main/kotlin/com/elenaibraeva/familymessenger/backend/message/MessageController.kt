
package com.elenaibraeva.familymessenger.backend.message

import com.elenaibraeva.familymessenger.backend.dev.DevUserProvider
import java.util.UUID
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@Profile("dev")
@RequestMapping("/api/v1/chats/{chatId}/messages")
class MessageController(
    private val messageService: MessageService,
    private val devUserProvider: DevUserProvider
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun sendMessage(
        @PathVariable chatId: UUID,
        @RequestBody request: SendMessageRequest
    ): MessageResponse = messageService.sendMessage(
        chatId = chatId,
        senderId = devUserProvider.userId,
        content = request.content
    ).toResponse()

    @GetMapping
    fun getMessages(
        @PathVariable chatId: UUID,
        @RequestParam(defaultValue = "50") limit: Int,
        @RequestParam(required = false) cursor: String?
    ): MessagePageResponse = messageService.getMessages(
        chatId = chatId,
        userId = devUserProvider.userId,
        limit = limit,
        cursor = cursor
    ).toResponse()
}
