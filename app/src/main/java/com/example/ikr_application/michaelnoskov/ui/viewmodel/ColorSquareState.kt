package com.example.ikr_application.michaelnoskov.ui.viewmodel

import com.example.ikr_application.michaelnoskov.data.models.FilteredItem
import com.example.ikr_application.michaelnoskov.data.models.SquareState

data class ColorSquareState(
    val squareState: SquareState = SquareState(
        color = 0xFF6200EE.toInt(),
        size = 200,
        rotation = 0f,
        alpha = 1f
    ),
    val filteredItems: List<FilteredItem> = emptyList(),
    val searchQuery: String = "",
    val itemsCount: Int = 0,
    val chartData: List<Pair<String, Float>> = emptyList(),
    val isLoading: Boolean = false
)