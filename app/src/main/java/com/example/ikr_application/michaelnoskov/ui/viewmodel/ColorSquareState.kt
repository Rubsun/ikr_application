package com.example.ikr_application.michaelnoskov.ui.viewmodel

import com.example.ikr_application.michaelnoskov.domain.model.ChartData
import com.example.ikr_application.michaelnoskov.domain.model.FilteredItem
import com.example.ikr_application.michaelnoskov.domain.model.SquareData

data class ColorSquareState(
    val squareState: SquareData = SquareData(
        id = "default",
        color = 0xFF6200EE.toInt(),
        size = 200,
        rotation = 0f,
        alpha = 1f
    ),
    val filteredItems: List<FilteredItem> = emptyList(),
    val searchQuery: String = "",
    val itemsCount: Int = 0,
    val chartData: List<ChartData> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)