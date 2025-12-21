package com.stupishin.api.domain.usecases

import com.stupishin.api.domain.models.Anime

interface GetTopAnimeUseCase {
    suspend operator fun invoke(): Result<List<Anime>>
}
