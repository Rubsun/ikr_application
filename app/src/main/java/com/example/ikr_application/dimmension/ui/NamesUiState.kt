package com.example.ikr_application.dimmension.ui

import com.example.ikr_application.dimmension.domain.models.NameDisplayModel

data class NamesUiState(
    val randomName: NameDisplayModel? = null,
    val namesList: List<NameDisplayModel> = emptyList(),
    val searchQuery: String = "",
    val filteredNamesList: List<NameDisplayModel> = emptyList(),
    val isLoading: Boolean = false
)

