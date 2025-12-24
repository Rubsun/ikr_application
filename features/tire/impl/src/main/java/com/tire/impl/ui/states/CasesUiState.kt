package com.tire.impl.ui.states

import com.tire.api.domain.models.CaseOpenResult
import com.tire.api.domain.models.PokemonCase

internal data class CasesUiState(
    val isLoading: Boolean = false,
    val cases: List<PokemonCase> = emptyList(),
    val lastOpenResult: CaseOpenResult? = null,
    val error: String? = null
)