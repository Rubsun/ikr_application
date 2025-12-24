package com.stupishin.impl.domain

import com.stupishin.api.domain.models.Anime
import com.stupishin.api.domain.usecases.SearchAnimeUseCase
import com.stupishin.impl.data.AnimeRepository
import com.stupishin.impl.data.StupishinStorage
import kotlin.Result.Companion.success

internal class SearchAnimeUseCaseImpl(
    private val repository: AnimeRepository,
    private val storage: StupishinStorage,
) : SearchAnimeUseCase {

    override suspend fun invoke(query: String): Result<List<Anime>> {
        if (query.isBlank()) {
            storage.saveQuery("")
            return success(emptyList())
        }

        return try {
            val items = repository.searchAnime(query = query, page = 1, limit = 25)
            storage.saveQuery(query)
            storage.saveItems(items)
            Result.success(items)
        } catch (e: Throwable) {
            val cached = storage.readItems()
            if (cached.isNotEmpty()) {
                Result.success(cached)
            } else {
                Result.failure(e)
            }
        }
    }
}
