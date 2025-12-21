package com.stupishin.impl.data

import com.stupishin.impl.data.models.AnimeDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AnimeRepository(
    private val api: AnimeService,
) {
    suspend fun topAnime(page: Int = 1): List<AnimeDto> {
        return withContext(Dispatchers.IO) {
            api.topAnime(page = page).data
        }
    }

    suspend fun searchAnime(query: String, page: Int = 1, limit: Int = 25): List<AnimeDto> {
        return withContext(Dispatchers.IO) {
            api.searchAnime(query = query, page = page, limit = limit).data
        }
    }
}
