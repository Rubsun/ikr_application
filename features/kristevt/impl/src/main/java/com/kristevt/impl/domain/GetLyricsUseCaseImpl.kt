package com.kristevt.impl.domain

import com.kristevt.lyrics.api.LyricsDataSource
import com.kristevt.api.domain.GetLyricsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GetLyricsUseCaseImpl(
    private val lyricsDataSource: LyricsDataSource
) : GetLyricsUseCase {

    override suspend fun invoke(
        artist: String,
        title: String
    ): Result<String> =
        withContext(Dispatchers.IO) {
            lyricsDataSource.getLyrics(artist, title)
        }
}
