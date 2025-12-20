package com.alexcode69.impl.ui

import com.alexcode69.api.domain.models.TimeEntry

internal data class TimerUiState(
    val timeEntries: List<TimeEntry> = emptyList(),
    val currentDate: String = "",
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)

