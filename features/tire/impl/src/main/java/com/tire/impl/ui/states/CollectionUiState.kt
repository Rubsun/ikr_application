package com.tire.impl.ui.states

import com.tire.api.domain.models.CollectionStats
import com.tire.api.domain.models.Pokemon

internal data class CollectionUiState(
    val isLoading: Boolean = false,
    val pokemons: List<Pokemon> = emptyList(),
    val stats: CollectionStats? = null,
    val error: String? = null
)