package com.example.ikr_application.michaelnoskov.data.models

data class FilteredItem(
    val id: Long,
    val text: String,
    val timestamp: Long,
    val isVisible: Boolean = true
)