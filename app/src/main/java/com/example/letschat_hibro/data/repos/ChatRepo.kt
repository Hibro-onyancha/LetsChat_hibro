package com.example.letschat_hibro.data.repos

import com.example.letschat_hibro.data.models.Chat
import com.example.letschat_hibro.data.models.UpdateChatRequest

interface ChatRepo {
    suspend fun getAllChats(): List<Chat>
    suspend fun updateChat(updateChatRequest: UpdateChatRequest)

    companion object {
        const val BASE_URL = "http://192.168.133.157:8080"
        /*192.168.133.157*/
    }

    sealed class EndPoints(val url: String) {
        object GetAllChats : EndPoints("$BASE_URL/chats")
        object UpdateChat : EndPoints(url = "$BASE_URL/chats/update-chat")
    }
}