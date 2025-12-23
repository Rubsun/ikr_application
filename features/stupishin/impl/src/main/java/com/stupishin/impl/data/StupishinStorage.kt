package com.stupishin.impl.data

import com.example.primitivestorage.api.PrimitiveStorage
import com.stupishin.api.domain.models.Anime
import kotlinx.serialization.Serializable
import kotlinx.coroutines.flow.first

internal class StupishinStorage(
    private val storage: PrimitiveStorage<State>,
) {
    suspend fun saveQuery(query: String) {
        storage.patch { old ->
            (old ?: State()).copy(query = query)
        }
    }

    suspend fun readQuery(): String {
        return storage.get().first()?.query.orEmpty()
    }

    suspend fun saveItems(items: List<Anime>) {
        val cached = items.map { CachedAnime(id = it.id, title = it.title, imageUrl = it.imageUrl) }
        storage.patch { old ->
            (old ?: State()).copy(items = cached)
        }
    }

    suspend fun readItems(): List<Anime> {
        val items = storage.get().first()?.items.orEmpty()
        return items.map { Anime(id = it.id, title = it.title, imageUrl = it.imageUrl) }
    }

    @Serializable
    internal data class State(
        val query: String = "",
        val items: List<CachedAnime> = emptyList(),
    )

    @Serializable
    internal data class CachedAnime(
        val id: Int,
        val title: String,
        val imageUrl: String?,
    )
}
