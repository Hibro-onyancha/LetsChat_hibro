package com.example.letschat_hibro.utils

import com.example.letschat_hibro.data.models.Address

fun getAddressList(): List<Address> {
    return listOf(
        Address(
            country = "Kenya",
            city = "Nairobi",
            street = "Ngong Road"
        ),
        Address(
            country = "USA",
            city = "New York",
            street = "5th Avenue"
        ),
        Address(
            country = "UK",
            city = "London",
            street = "Baker Street"
        ),
        Address(
            country = "Japan",
            city = "Tokyo",
            street = "Shibuya"
        ),
        Address(
            country = "Australia",
            city = "Sydney",
            street = "George Street"
        )
    )
}