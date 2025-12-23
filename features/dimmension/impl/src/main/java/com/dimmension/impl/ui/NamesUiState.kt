package com.dimmension.impl.ui

import com.dimmension.api.domain.models.NameDisplayModel

internal data class NamesUiState(
    val randomName: NameDisplayModel? = null,
    val namesList: List<NameDisplayModel> = emptyList(),
    val filteredNamesList: List<NameDisplayModel> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
)


