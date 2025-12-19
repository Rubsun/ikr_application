package com.stupishin.api.domain.usecases

import com.stupishin.api.domain.models.Anime

interface SearchAnimeUseCase {
    suspend operator fun invoke(query: String): Result<List<Anime>>
}
