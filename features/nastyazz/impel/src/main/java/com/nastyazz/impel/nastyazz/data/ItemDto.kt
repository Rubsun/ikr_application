package com.nastyazz.impel.nastyazz.data

import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query

@Serializable
data class ItemDto(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: String
)

@Serializable
data class ItemResponseDto(
    val products: List<ItemDto>
)

@Serializable
internal data class ItemSuggestDto(
    val query: String
)

internal interface NastyAzzApi {
    @GET("products/search")
    suspend fun search(
        @Query("q") query: String
    ): ItemResponseDto
}