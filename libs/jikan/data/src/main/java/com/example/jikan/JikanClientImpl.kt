package com.example.jikan

import com.example.jikan.api.JikanAnime
import com.example.jikan.api.JikanClient
import com.example.network.api.RetrofitServiceFactory

private const val BASE_URL = "https://api.jikan.moe/"

internal class JikanClientImpl(
    private val retrofitServiceFactory: RetrofitServiceFactory,
) : JikanClient {

    private val service: JikanService by lazy {
        retrofitServiceFactory.create(BASE_URL, JikanService::class.java)
    }

    override suspend fun topAnime(page: Int): List<JikanAnime> {
        return service.topAnime(page = page).data.map(::mapAnime)
    }

    override suspend fun searchAnime(query: String, page: Int, limit: Int): List<JikanAnime> {
        return service.searchAnime(query = query, page = page, limit = limit).data.map(::mapAnime)
    }

    private fun mapAnime(dto: AnimeDto): JikanAnime {
        return JikanAnime(
            id = dto.id,
            title = dto.titleEnglish?.takeIf { it.isNotBlank() } ?: dto.title,
            imageUrl = dto.images?.jpg?.imageUrl,
        )
    }
}
