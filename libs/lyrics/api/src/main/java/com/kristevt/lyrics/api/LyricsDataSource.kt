package com.kristevt.lyrics.api

interface LyricsDataSource {
    suspend fun getLyrics(
        artist: String,
        title: String
    ): Result<String>
}
