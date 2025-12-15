package com.example.ikr_application.nastyazz.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class FakeItemRepository : ItemRepository {

    private val items = mutableListOf<ItemDto>()
    private val itemsFlow = MutableStateFlow<List<ItemDto>>(emptyList())

    init {
        items.addAll(
            List(10) { index ->
                ItemDto(
                    id = index + 1,
                    title = "Item #${index + 1}",
                    description = "Описание элемента №${index + 1}",
                    imageUrl = "https://picsum.photos/200?random=$index"
                )
            }
        )
        itemsFlow.value = items
    }

    override fun observeItems(): StateFlow<List<ItemDto>> = itemsFlow

    override suspend fun addItem(item: ItemDto) {
        withContext(Dispatchers.IO) {
            items.add(item)
            itemsFlow.value = items.toList()
        }
    }
}