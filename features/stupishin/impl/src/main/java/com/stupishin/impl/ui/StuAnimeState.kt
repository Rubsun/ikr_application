package com.stupishin.impl.ui

import com.stupishin.api.domain.models.Anime

internal data class StuAnimeState(
    val query: String = "",
    val isLoading: Boolean = false,
    val items: List<Anime> = emptyList(),
    val error: String? = null,
)
