package com.example.jikan

import retrofit2.http.GET
import retrofit2.http.Query

internal interface JikanService {
    @GET("v4/top/anime")
    suspend fun topAnime(
        @Query("page") page: Int = 1,
    ): AnimeListResponseDto

    @GET("v4/anime")
    suspend fun searchAnime(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25,
    ): AnimeListResponseDto
}
