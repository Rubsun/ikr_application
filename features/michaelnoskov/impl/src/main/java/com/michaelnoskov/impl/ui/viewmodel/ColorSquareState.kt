package com.michaelnoskov.impl.ui.viewmodel

import com.michaelnoskov.api.domain.model.ChartData
import com.michaelnoskov.api.domain.model.FilteredItem
import com.michaelnoskov.api.domain.model.SquareData

internal data class ColorSquareState(
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
    val error: String? = null,
    val currentTemperature: Double? = null,
    val temperatureHistory: List<com.michaelnoskov.api.domain.repository.TemperaturePoint> = emptyList()
)

