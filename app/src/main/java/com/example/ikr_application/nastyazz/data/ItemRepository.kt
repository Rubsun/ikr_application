package com.example.ikr_application.nastyazz.data

interface ItemRepository {
    suspend fun fetchItems(): List<ItemDto>
    suspend fun fetchItemById(id: Int): ItemDto?
}