package com.alexcode69.api.domain.models

data class TimeEntry(
    val id: String,
    val timestamp: Long,
    val label: String,
    val isActive: Boolean = false,
)

