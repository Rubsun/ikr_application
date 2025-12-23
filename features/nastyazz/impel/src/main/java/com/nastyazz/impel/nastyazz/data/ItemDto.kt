package com.nastyazz.impel.nastyazz.data

import kotlinx.serialization.Serializable
import retrofit2.http.GET

@Serializable
data class ItemDto(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
)

@Serializable
data class ItemsResponseDto(
    val items: List<ItemDto>
)

interface NastyAzzApi {
    @GET("items")
    suspend fun getItems(): ItemsResponseDto
}
