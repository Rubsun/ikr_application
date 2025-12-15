package com.example.ikr_application.nastyazz.ui

data class ItemsState(
    val items: List<Item> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false
)
