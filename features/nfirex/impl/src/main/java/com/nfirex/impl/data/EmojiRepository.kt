package com.nfirex.impl.data

import com.example.primitivestorage.api.PrimitiveStorage
import com.nfirex.impl.data.models.EmojiDto
import com.nfirex.impl.data.models.EmojiSuggestDto
import kotlinx.coroutines.flow.first

private const val STORAGE_NAME = "nfirex_emoji_suggest.json"

internal class EmojiRepository(
    private val api: EmojiService,
    private val storageFactory: PrimitiveStorage.Factory,
) {
    private val storage by lazy {
        storageFactory.create(STORAGE_NAME, EmojiSuggestDto.serializer())
    }

    suspend fun searchEmojis(query: String): List<EmojiDto> {
        return api.searchEmojis(query)
    }

    suspend fun savedSuggest(): String? {
        return storage.get().first()?.query
    }

    suspend fun saveSuggest(query: String) {
        val suggest = EmojiSuggestDto(query)
        return storage.put(suggest)
    }
}