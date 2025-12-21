package com.stupishin.impl.data

import android.content.SharedPreferences
import com.stupishin.api.domain.models.Anime
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class StupishinStorage(
    private val prefs: SharedPreferences,
    private val json: Json,
) {
    fun saveQuery(query: String) {
        prefs.edit().putString(KEY_QUERY, query).apply()
    }

    fun readQuery(): String {
        return prefs.getString(KEY_QUERY, "").orEmpty()
    }

    fun saveItems(items: List<Anime>) {
        val cached = items.map { CachedAnime(id = it.id, title = it.title, imageUrl = it.imageUrl) }
        prefs.edit().putString(KEY_ITEMS, json.encodeToString(cached)).apply()
    }

    fun readItems(): List<Anime> {
        val str = prefs.getString(KEY_ITEMS, null) ?: return emptyList()

        return runCatching { json.decodeFromString<List<CachedAnime>>(str) }
            .getOrNull()
            ?.map { Anime(id = it.id, title = it.title, imageUrl = it.imageUrl) }
            ?: emptyList()
    }

    @Serializable
    private data class CachedAnime(
        val id: Int,
        val title: String,
        val imageUrl: String?,
    )

    private companion object {
        const val KEY_QUERY = "query"
        const val KEY_ITEMS = "items"
    }
}
