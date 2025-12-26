package com.fomin.impl.ui.state

import com.fomin.api.domain.models.CatBreed
import com.fomin.api.domain.models.CatImage

internal data class BreedDetailState(
    val isLoading: Boolean = false,
    val breed: CatBreed? = null,
    val images: List<CatImage> = emptyList(),
    val error: String? = null,
)


