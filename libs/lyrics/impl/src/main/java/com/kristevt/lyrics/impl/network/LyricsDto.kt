package com.kristevt.lyrics.impl.network

import kotlinx.serialization.Serializable

@Serializable
internal data class LyricsDto(
    val lyrics: String
)
