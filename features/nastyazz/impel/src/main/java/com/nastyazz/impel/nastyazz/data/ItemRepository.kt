package com.nastyazz.impel.nastyazz.data
import kotlinx.coroutines.flow.StateFlow

internal interface ItemRepository {
    fun observeItems(): StateFlow<List<ItemDto>>
    suspend fun addItem(item: ItemDto)
}