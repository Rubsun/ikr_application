package com.stupishin.impl.data

import com.example.jikan.api.JikanAnime
import com.example.jikan.api.JikanClient
import com.stupishin.api.domain.models.Anime

internal interface AnimeService {
    suspend fun topAnime(page: Int = 1): List<Anime>

    suspend fun searchAnime(
        query: String,
        page: Int = 1,
        limit: Int = 25,
    ): List<Anime>
}

internal class AnimeServiceImpl(
    private val client: JikanClient,
) : AnimeService {
    override suspend fun topAnime(page: Int): List<Anime> {
        return client.topAnime(page = page).map(::mapAnime)
    }

    override suspend fun searchAnime(query: String, page: Int, limit: Int): List<Anime> {
        return client.searchAnime(query = query, page = page, limit = limit).map(::mapAnime)
    }

    private fun mapAnime(dto: JikanAnime): Anime {
        return Anime(
            id = dto.id,
            title = dto.title,
            imageUrl = dto.imageUrl,
        )
    }
}
