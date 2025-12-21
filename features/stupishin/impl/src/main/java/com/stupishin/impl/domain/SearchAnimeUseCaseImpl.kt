package com.stupishin.impl.domain

import com.stupishin.api.domain.models.Anime
import com.stupishin.api.domain.usecases.SearchAnimeUseCase
import com.stupishin.impl.data.AnimeRepository
import com.stupishin.impl.data.StupishinStorage
import com.stupishin.impl.data.models.AnimeDto
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

        val result = runCatching {
            repository.searchAnime(query = query, page = 1, limit = 25).map(::mapAnime)
        }

        result.onSuccess { items ->
            storage.saveQuery(query)
            storage.saveItems(items)
        }

        return result.recoverCatching { e ->
            val cached = storage.readItems()
            if (cached.isNotEmpty()) {
                cached
            } else {
                throw e
            }
        }
    }

    private fun mapAnime(dto: AnimeDto): Anime {
        return Anime(
            id = dto.id,
            title = dto.titleEnglish?.takeIf { it.isNotBlank() } ?: dto.title,
            imageUrl = dto.images?.jpg?.imageUrl,
        )
    }
}
