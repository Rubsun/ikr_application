package com.nastyazz.impel.nastyazz.data

import com.nastyazz.api.domain.models.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal interface ItemRepository {
    fun observeItems(): StateFlow<List<Item>>
    suspend fun addItem(item: Item)
}

internal class FakeItemRepository : ItemRepository {

    private val items = mutableListOf<Item>()
    private val itemsFlow = MutableStateFlow<List<Item>>(emptyList())

    init {
        items.addAll(List(10) { index ->
            Item(
                id = index + 1,
                title = "Item #${index + 1}",
                description = "Описание элемента №${index + 1}",
                imageUrl = "https://picsum.photos/200?random=$index"
            )
        })
        itemsFlow.value = items
    }

    override fun observeItems(): StateFlow<List<Item>> = itemsFlow

    override suspend fun addItem(item: Item) {
        withContext(Dispatchers.IO) {
            items.add(item)
            itemsFlow.value = items.toList()
        }
    }
}
