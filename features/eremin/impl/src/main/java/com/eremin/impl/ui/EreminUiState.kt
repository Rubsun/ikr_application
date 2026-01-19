package com.eremin.impl.ui

import com.eremin.api.domain.models.Capybara

internal data class EreminUiState(
    val capybaras: List<Capybara> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
