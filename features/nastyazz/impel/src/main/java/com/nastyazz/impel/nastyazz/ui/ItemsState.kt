package com.nastyazz.impel.nastyazz.ui

import com.nastyazz.impel.nastyazz.domain.Item

data class ItemsState(
    val items: List<Item> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false
)