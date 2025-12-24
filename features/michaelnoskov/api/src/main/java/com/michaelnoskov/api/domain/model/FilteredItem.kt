package com.michaelnoskov.api.domain.model

data class FilteredItem(
    val id: String,
    val text: String,
    val timestamp: Long,
    val isVisible: Boolean = true
)

