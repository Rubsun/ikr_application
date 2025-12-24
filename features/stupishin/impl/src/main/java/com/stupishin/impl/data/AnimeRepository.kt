package com.stupishin.impl.data

import com.stupishin.api.domain.models.Anime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AnimeRepository(
    private val api: AnimeService,
) {
    suspend fun topAnime(page: Int = 1): List<Anime> {
        return withContext(Dispatchers.IO) {
            api.topAnime(page = page)
        }
    }

    suspend fun searchAnime(query: String, page: Int = 1, limit: Int = 25): List<Anime> {
        return withContext(Dispatchers.IO) {
            api.searchAnime(query = query, page = page, limit = limit)
        }
    }
}
