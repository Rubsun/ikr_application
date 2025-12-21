package com.stupishin.impl.domain

import com.stupishin.api.domain.models.Anime
import com.stupishin.api.domain.usecases.GetTopAnimeUseCase
import com.stupishin.impl.data.AnimeRepository
import com.stupishin.impl.data.StupishinStorage
import com.stupishin.impl.data.models.AnimeDto

internal class GetTopAnimeUseCaseImpl(
    private val repository: AnimeRepository,
    private val storage: StupishinStorage,
) : GetTopAnimeUseCase {

    override suspend fun invoke(): Result<List<Anime>> {
        val result = runCatching {
            repository.topAnime(page = 1).map(::mapAnime)
        }

        result.onSuccess { items ->
            storage.saveQuery("")
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
