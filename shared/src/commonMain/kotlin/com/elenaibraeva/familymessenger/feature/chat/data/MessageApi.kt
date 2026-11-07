import com.elenaibraeva.familymessenger.feature.chat.data.MessageDto
import com.elenaibraeva.familymessenger.feature.chat.data.MessagePageDto
import com.elenaibraeva.familymessenger.feature.chat.data.SendMessageRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class MessageApi(
    private val client: HttpClient,
) {
    suspend fun getMessages(
        chatId: String,
        limit: Int = 50,
        cursor: String? = null,
    ): MessagePageDto =
        client.get("/api/v1/chats/$chatId/messages") {
            url {
                parameters.append("limit", limit.toString())
                cursor?.let { parameters.append("cursor", it) }
            }
        }.body()

    suspend fun sendMessage(
        chatId: String,
        content: String,
    ): MessageDto =
        client.post("/api/v1/chats/$chatId/messages") {
            contentType(ContentType.Application.Json)
            setBody(SendMessageRequest(content))
        }.body()
}
