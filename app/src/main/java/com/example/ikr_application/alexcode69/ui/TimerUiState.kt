package com.example.ikr_application.alexcode69.ui

import com.example.ikr_application.alexcode69.data.models.TimeEntry

data class TimerUiState(
    val timeEntries: List<TimeEntry> = emptyList(),
    val currentDate: String = "",
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)
