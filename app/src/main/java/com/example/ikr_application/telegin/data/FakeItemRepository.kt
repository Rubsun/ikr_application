package com.example.ikr_application.telegin.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ItemDto(
    val id: Int,
    val title: String,
    val description: String,
)

class FakeItemRepository {

    private val items = MutableStateFlow(
        listOf(
            ItemDto(1, "First", "First item"),
            ItemDto(2, "Second", "Second item"),
            ItemDto(3, "Third", "Third item"),
        )
    )

    fun observeItems(): Flow<List<ItemDto>> = items.asStateFlow()

    suspend fun addItem(title: String, description: String) {
        val current = items.value
        val nextId = (current.maxOfOrNull { it.id } ?: 0) + 1
        items.value = current + ItemDto(nextId, title, description)
    }
}
