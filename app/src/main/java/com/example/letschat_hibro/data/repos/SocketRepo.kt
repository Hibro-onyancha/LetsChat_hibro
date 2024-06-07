package com.example.letschat_hibro.data.repos

import com.example.letschat_hibro.data.models.Chat
import com.example.letschat_hibro.data.models.Message
import com.example.letschat_hibro.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SocketRepo {
    suspend fun initiateSession(username: String): Resource<Unit>
    suspend fun closeSession()
    suspend fun observeChat(): Flow<Chat>
    suspend fun sendChat(chat: Chat)

    companion object {
        const val BASE_URL = "ws://192.168.133.157:8080"
    }

    sealed class EndPoints(val url: String) {
        object SocketEndPoint : EndPoints("$BASE_URL/chat-socket")
    }

}