package com.example.letschat_hibro.presentation.main_screen

import com.example.letschat_hibro.data.models.Chat

data class ChatState(
    val isLoading: Boolean = false,
    val allChats: List<Chat> = listOf(),
    val userName: String = ""
)
