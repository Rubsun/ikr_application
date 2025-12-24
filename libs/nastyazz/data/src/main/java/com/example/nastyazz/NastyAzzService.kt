package com.example.nastyazz

import retrofit2.http.GET
import retrofit2.http.Query

internal interface NastyAzzService {
    @GET("products/search")
    suspend fun search(
        @Query("q") query: String,
    ): ItemResponseDto
}
