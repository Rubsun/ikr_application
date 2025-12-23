package com.kristevt.impl.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class SongDto (
    val title: String,
    val author: String,
    val album: String,
    val year: Long,
    val genre: String
)