package com.stupishin.impl.data

import com.stupishin.impl.data.models.AnimeListResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface AnimeService {
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
