package com.grigoran.impl.data

import com.example.primitivestorage.api.PrimitiveStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import kotlin.random.Random
import retrofit2.http.GET
import retrofit2.http.Query
import com.grigoran.network.api.ApiClient
import com.grigoran.network.api.Item


@Serializable
internal data class ItemSuggestDto(
    val query: String
)

private const val STORAGE_NAME = "grigoran_item_suggest.json"

internal class Repository (
    private val api: ApiClient,
    private val storageFactory: PrimitiveStorage.Factory
) {
    private val storage by lazy {
        storageFactory.create(STORAGE_NAME, ItemSuggestDto.serializer())
    }
    suspend fun search(query: String): List<Item> {
        return api.search(query)
    }

    suspend fun savedSuggest(): String? {
        return storage.get().first()?.query
    }

    suspend fun saveSuggest(query: String) {
        storage.put(ItemSuggestDto(query))
    }
}