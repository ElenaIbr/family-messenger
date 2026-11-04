
package com.elenaibraeva.familymessenger.backend.message

import com.elenaibraeva.familymessenger.backend.chat.ChatMemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class MessageService(
    private val messageRepository: MessageRepository,
    private val chatMemberRepository: ChatMemberRepository,
) {

    @Transactional
    fun sendMessage(
        chatId: UUID,
        senderId: UUID,
        content: String,
    ): Message {
        require(content.isNotBlank()) {
            "Message content cannot be blank"
        }

        require(content.length <= 4000) {
            "Message content exceeds 4000 characters"
        }

        if (!chatMemberRepository.isMember(chatId, senderId)) {
            throw ChatNotFoundException()
        }

        return messageRepository.save(
            chatId = chatId,
            senderId = senderId,
            content = content,
        )
    }
}

class ChatNotFoundException :
    RuntimeException("Chat not found")
