package com.nastyazz.impel.nastyazz.data

import com.example.nastyazz.api.NastyAzzClient
import com.example.nastyazz.api.NastyItem
import com.example.primitivestorage.api.PrimitiveStorage
import kotlinx.coroutines.flow.first

private const val STORAGE_NAME = "nastyazz_item_suggest.json"

internal class ItemRepository(
    private val client: NastyAzzClient,
    private val storageFactory: PrimitiveStorage.Factory
) {

    private val storage by lazy {
        storageFactory.create(STORAGE_NAME, ItemSuggestDto.serializer())
    }

    suspend fun search(query: String): List<NastyItem> {
        return client.searchProducts(query)
    }

    suspend fun savedSuggest(): String? {
        return storage.get().first()?.query
    }

    suspend fun saveSuggest(query: String) {
        storage.put(ItemSuggestDto(query))
    }
}