package com.example.ikr_application.stupishin.domain

import com.example.ikr_application.stupishin.data.AnimeRepository
import com.example.ikr_application.stupishin.domain.models.Anime

internal class GetTopAnimeUseCase(
    private val repository: AnimeRepository = AnimeRepository.INSTANCE,
) {
    suspend fun execute(page: Int = 1): List<Anime> {
        return repository.topAnime(page = page).map { dto ->
            Anime(
                id = dto.id,
                title = dto.titleEnglish?.takeIf { it.isNotBlank() } ?: dto.title,
                imageUrl = dto.images?.jpg?.imageUrl,
            )
        }
    }
}
