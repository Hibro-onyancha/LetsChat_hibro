package com.example.letschat_hibro.domain.repoImpl

import android.util.Log
import com.example.letschat_hibro.data.models.Chat
import com.example.letschat_hibro.data.models.UpdateChatRequest
import com.example.letschat_hibro.data.repos.ChatRepo
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ChatRepoImpl(
    private val client: HttpClient
) : ChatRepo {

    override suspend fun getAllChats(): List<Chat> {
        return try {
            client.get<List<Chat>>(ChatRepo.EndPoints.GetAllChats.url)
        } catch (e: Exception) {
            Log.e("all-chats", "$e")
            emptyList()
        }
    }

    override suspend fun updateChat(updateChatRequest: UpdateChatRequest) {
        try {

            client.post<String>(ChatRepo.EndPoints.UpdateChat.url) {
                contentType(ContentType.Application.Json)
                body = updateChatRequest
            }
        } catch (e: Exception) {
           Log.e( "update","Error: ${e.localizedMessage}")
        } finally {
            client.close()
        }
    }
}