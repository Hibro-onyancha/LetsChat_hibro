package com.example.letschat_hibro.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val id: String = "",
    val country: String,
    val city: String,
    val street: String
)
