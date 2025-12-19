package com.grigoran.impl.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

internal data class ItemDto(var id: Int, var title: String, val price: Int)

internal class Repository {
    private val itemsFlow = MutableStateFlow( List(Random.nextInt(5, 15)) { index ->
        ItemDto(
            id = index + 1,
            title = "title ${index + 1}",
            price = Random.nextInt(1000, 500000)
        )
    })
    fun observeItems() = itemsFlow.asStateFlow()

    fun addItem(title: String, price: Int) {
        val current = itemsFlow.value.toMutableList()
        val newId = (current.maxOfOrNull { it.id } ?: 0) + 1
        current.add(ItemDto(newId, title, price))
        itemsFlow.value = current
    }
}