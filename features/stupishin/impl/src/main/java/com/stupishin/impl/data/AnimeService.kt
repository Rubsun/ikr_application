package com.stupishin.impl.data

import com.example.jikan.api.JikanAnime
import com.example.jikan.api.JikanClient

internal interface AnimeService {
    suspend fun topAnime(page: Int = 1): List<JikanAnime>

    suspend fun searchAnime(
        query: String,
        page: Int = 1,
        limit: Int = 25,
    ): List<JikanAnime>
}

internal class AnimeServiceImpl(
    private val client: JikanClient,
) : AnimeService {
    override suspend fun topAnime(page: Int): List<JikanAnime> {
        return client.topAnime(page = page)
    }

    override suspend fun searchAnime(query: String, page: Int, limit: Int): List<JikanAnime> {
        return client.searchAnime(query = query, page = page, limit = limit)
    }
}
