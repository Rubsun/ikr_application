package com.tire.impl.ui.states

import com.tire.api.domain.models.Pokemon

internal data class CasePreviewUiState(
    val isLoading: Boolean = false,
    val pokemons: List<Pokemon> = emptyList(),
    val caseName: String = "",
    val caseInfo: String = "",
    val caseImageUrl: String? = null,
    val error: String? = null
)


