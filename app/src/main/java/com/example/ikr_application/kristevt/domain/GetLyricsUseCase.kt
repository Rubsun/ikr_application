package com.example.ikr_application.kristevt.domain

import com.example.ikr_application.kristevt.data.LyricsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GetLyricsUseCase(
    private val repository: LyricsRepository = LyricsRepository.INSTANCE
) {

    suspend operator fun invoke(artist: String, title: String): Result<String> =
        withContext(Dispatchers.IO) {
            runCatching {
                repository.getLyrics(artist, title).lyrics
            }
        }
}
