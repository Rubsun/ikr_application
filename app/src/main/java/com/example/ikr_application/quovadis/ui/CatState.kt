package com.example.ikr_application.quovadis.ui

import com.example.ikr_application.quovadis.data.Cat

data class CatUiState(
    val isLoading: Boolean = false,
    val cats: List<Cat> = emptyList(),
    val filter: String = "",
    val error: String? = null
)