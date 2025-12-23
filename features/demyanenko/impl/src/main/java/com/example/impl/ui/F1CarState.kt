package com.example.impl.ui

import com.example.demyanenko.impl.ui.DemyanenkoItem

internal data class F1CarState(
    val cars: List<DemyanenkoItem.CarItem> = emptyList(),
    val drivers: List<DemyanenkoItem.DriverItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
) {
    val items: List<DemyanenkoItem>
        get() = drivers + cars
}
