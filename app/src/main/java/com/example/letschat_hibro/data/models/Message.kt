package com.example.letschat_hibro.data.models

data class Message(
    var message: String,
    var time: String,
    var userName: String,
    val id: String = ""
)