package com.stupishin.impl.domain

import com.stupishin.api.domain.models.Anime
import com.stupishin.api.domain.usecases.GetTopAnimeUseCase
import com.stupishin.impl.data.AnimeRepository
import com.stupishin.impl.data.StupishinStorage

internal class GetTopAnimeUseCaseImpl(
    private val repository: AnimeRepository,
    private val storage: StupishinStorage,
) : GetTopAnimeUseCase {

    override suspend fun invoke(): Result<List<Anime>> {
        return try {
            val items = repository.topAnime(page = 1)
            storage.saveQuery("")
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
