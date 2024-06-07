package com.example.letschat_hibro.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UpdateChatRequest(val id: String, val newChat: Chat)
