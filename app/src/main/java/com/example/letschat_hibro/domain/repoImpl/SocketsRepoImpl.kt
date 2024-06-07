package com.example.letschat_hibro.domain.repoImpl

import android.util.Log
import com.example.letschat_hibro.data.models.Chat
import com.example.letschat_hibro.data.models.Message
import com.example.letschat_hibro.data.repos.SocketRepo
import com.example.letschat_hibro.utils.Resource
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SocketsRepoImpl(private val client: HttpClient) : SocketRepo {
    private var socket: WebSocketSession? = null
    override suspend fun initiateSession(username: String): Resource<Unit> {
        return try {
            socket = client.webSocketSession {
                url("${SocketRepo.EndPoints.SocketEndPoint.url}?username=$username")
            }
            if (socket?.isActive == true) {
                Resource.Success(Unit)
            } else Resource.Error("Couldn't establish a connection.")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("initiateSession", "$e")
            Resource.Error(e.localizedMessage ?: "Unknown error")

        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }

    override suspend fun observeChat(): Flow<Chat> {
        return try {
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json = (it as? Frame.Text)?.readText() ?: ""
                    val messageDto = Json.decodeFromString<Chat>(json)
                    messageDto
                } ?: flow { }
        } catch (e: Exception) {
            e.printStackTrace()
            flow { }
        }
    }

    override suspend fun sendChat(chat: Chat) {
        try {
            val jsonString = Json.encodeToString(chat)
            socket?.send(Frame.Text(jsonString))
        } catch (e: Exception) {
            Log.e("send_text", "$e")
            e.printStackTrace()
        }
    }
}