package com.example.jikan.api

interface JikanClient {
    suspend fun topAnime(page: Int = 1): List<JikanAnime>

    suspend fun searchAnime(
        query: String,
        page: Int = 1,
        limit: Int = 25,
    ): List<JikanAnime>
}
