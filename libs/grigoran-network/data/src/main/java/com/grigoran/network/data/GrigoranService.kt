package com.grigoran.network.data

import retrofit2.http.GET
import retrofit2.http.Query

internal interface ItemService {

    @GET("products/search")
    suspend fun search(
        @Query("q") query: String
    ): ItemResponseDto
}