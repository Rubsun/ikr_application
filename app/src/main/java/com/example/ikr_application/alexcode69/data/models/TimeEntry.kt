package com.example.ikr_application.alexcode69.data.models

data class TimeEntry(
    val id: String,
    val timestamp: Long,
    val label: String,
    val isActive: Boolean = false,
)
