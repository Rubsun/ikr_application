package com.grigoran.api.models

data class Item(
    val id: Int,
    val title: String,
    val price: Double,
    val ItemUrl: String
)

data class ItemResult(
    val query: String,
    val items: List<Item> = emptyList(),
    val error: Throwable? = null
)