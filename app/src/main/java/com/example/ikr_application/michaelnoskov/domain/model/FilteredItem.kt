package com.example.ikr_application.michaelnoskov.domain.model

data class FilteredItem(
    val id: String,
    val text: String,
    val timestamp: Long,
    val isVisible: Boolean = true
)