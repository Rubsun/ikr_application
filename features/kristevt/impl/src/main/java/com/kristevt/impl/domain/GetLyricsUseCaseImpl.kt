package com.kristevt.impl.domain

import com.kristevt.api.domain.GetLyricsUseCase
import com.kristevt.impl.data.LyricsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GetLyricsUseCaseImpl(
    private val repository: LyricsRepository = LyricsRepository.INSTANCE
) : GetLyricsUseCase {

    override suspend operator fun invoke(artist: String, title: String): Result<String> =
        withContext(Dispatchers.IO) {
            runCatching {
                repository.getLyrics(artist, title).lyrics
            }
        }
}
