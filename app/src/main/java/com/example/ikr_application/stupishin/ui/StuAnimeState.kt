package com.example.ikr_application.stupishin.ui

import com.example.ikr_application.stupishin.domain.models.Anime

data class StuAnimeState(
    val query: String = "",
    val isLoading: Boolean = false,
    val items: List<Anime> = emptyList(),
    val error: String? = null,
)
