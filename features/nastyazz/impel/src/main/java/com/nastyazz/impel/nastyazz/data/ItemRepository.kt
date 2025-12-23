package com.nastyazz.impel.nastyazz.data

import com.example.primitivestorage.api.PrimitiveStorage
import kotlinx.coroutines.flow.first

private const val STORAGE_NAME = "nastyazz_item_suggest.json"

internal class ItemRepository(
    private val api: NastyAzzApi,
    private val storageFactory: PrimitiveStorage.Factory
) {
    private val storage by lazy {
        storageFactory.create(STORAGE_NAME, ItemSuggestDto.serializer())
    }

    suspend fun search(query: String): List<ItemDto> {
        return api.search(query).products
    }

    suspend fun savedSuggest(): String? {
        return storage.get().first()?.query
    }

    suspend fun saveSuggest(query: String) {
        storage.put(ItemSuggestDto(query))
    }
}