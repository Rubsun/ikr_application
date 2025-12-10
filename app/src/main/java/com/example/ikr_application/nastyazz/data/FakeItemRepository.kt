package com.example.ikr_application.nastyazz.data

import kotlinx.coroutines.delay

class FakeItemRepository : ItemRepository {

    private val items = List(10) { index ->
        ItemDto(
            id = index + 1,
            title = "Item #${index + 1}",
            description = "Описание элемента №${index + 1}"
        )
    }

    override suspend fun fetchItems(): List<ItemDto> {
        delay(300)
        return items
    }

    override suspend fun fetchItemById(id: Int): ItemDto? {
        delay(150)
        return items.firstOrNull { it.id == id }
    }
}