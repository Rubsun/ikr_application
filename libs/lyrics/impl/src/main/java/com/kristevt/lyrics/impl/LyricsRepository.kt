package com.kristevt.lyrics.impl

import com.kristevt.lyrics.api.LyricsDataSource
import com.kristevt.lyrics.impl.network.LyricsService

internal class LyricsRepository(
    private val service: LyricsService
) : LyricsDataSource {

    override suspend fun getLyrics(
        artist: String,
        title: String
    ): Result<String> =
        runCatching {
            service.getLyrics(artist, title).lyrics
        }
}
