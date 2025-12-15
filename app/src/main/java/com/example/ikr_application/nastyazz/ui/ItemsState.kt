package com.example.ikr_application.nastyazz.ui

import com.example.ikr_application.nastyazz.domain.Item

data class ItemsState(
    val items: List<Item> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false
)