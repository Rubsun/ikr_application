package com.example.ikr_application.nastyazz.data
import kotlinx.coroutines.flow.StateFlow

interface ItemRepository {

    fun observeItems(): StateFlow<List<ItemDto>>

    suspend fun addItem(item: ItemDto)
}