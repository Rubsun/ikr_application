package com.example.ikr_application.stupishin.domain

import com.example.ikr_application.stupishin.data.AnimeRepository
import com.example.ikr_application.stupishin.domain.models.Anime

internal class SearchAnimeUseCase(
    private val repository: AnimeRepository = AnimeRepository.INSTANCE,
) {
    suspend fun execute(query: String, page: Int = 1, limit: Int = 25): List<Anime> {
        return repository.searchAnime(query = query, page = page, limit = limit).map { dto ->
            Anime(
                id = dto.id,
                title = dto.titleEnglish?.takeIf { it.isNotBlank() } ?: dto.title,
                imageUrl = dto.images?.jpg?.imageUrl,
            )
        }
    }
}
