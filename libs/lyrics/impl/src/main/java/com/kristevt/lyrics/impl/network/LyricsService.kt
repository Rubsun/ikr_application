package com.kristevt.lyrics.impl.network

import retrofit2.http.GET
import retrofit2.http.Path

internal interface LyricsService {

    @GET("v1/{artist}/{title}")
    suspend fun getLyrics(
        @Path("artist") artist: String,
        @Path("title") title: String
    ): LyricsDto
}
