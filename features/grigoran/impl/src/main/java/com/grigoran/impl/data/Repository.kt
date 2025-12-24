package com.grigoran.impl.data

import com.example.primitivestorage.api.PrimitiveStorage
import com.grigoran.api.models.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import kotlin.random.Random
import retrofit2.http.GET
import retrofit2.http.Query


@Serializable
internal data class ItemDto(val id: Int, val title: String, val price: Double, val thumbnail: String)

@Serializable
internal data class ItemResponseDto(
    val products: List<ItemDto>
)

@Serializable
internal data class ItemSuggestDto(
    val query: String
)

internal interface ItemService {

    @GET("products/search")
    suspend fun search(
        @Query("q") query: String
    ): ItemResponseDto
}

private const val STORAGE_NAME = "grigoran_item_suggest.json"

internal class Repository (
    private val api: ItemService,
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