package com.fomin.impl.ui.state

import com.fomin.api.domain.models.CatBreed

internal data class FominState(
    val query: String = "",
    val isLoading: Boolean = false,
    val breeds: List<CatBreed> = emptyList(),
    val error: String? = null,
)


