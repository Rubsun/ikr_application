package com.example.nastyazz

import kotlinx.serialization.Serializable

@Serializable
internal data class ItemDto(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: String,
)

@Serializable
internal data class ItemResponseDto(
    val products: List<ItemDto>,
)
