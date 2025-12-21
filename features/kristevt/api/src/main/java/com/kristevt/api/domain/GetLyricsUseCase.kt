package com.kristevt.api.domain

interface GetLyricsUseCase{
    suspend operator fun invoke(artist: String, title: String): Result<String>
}
