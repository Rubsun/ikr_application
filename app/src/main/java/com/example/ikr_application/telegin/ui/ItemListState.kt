package com.example.ikr_application.telegin.ui

import com.example.ikr_application.telegin.domain.Item

data class ItemListState(
    val isLoading: Boolean = false,
    val query: String = "",
    val items: List<Item> = emptyList(),
)
