package com.example.letschat_hibro.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Chat(
    var id: String = "",
    var message: String,
    var time: String,
    var userName: String,
    val address: Address = Address(city = "Nairobi", country = "kenya", street = "nagware"),
    val addressList: List<Address> = listOf()
) {

    fun toMessage(): Message {

        return Message(
            message = message,
            time = time,
            userName = userName,
            id = id
        )
    }
}