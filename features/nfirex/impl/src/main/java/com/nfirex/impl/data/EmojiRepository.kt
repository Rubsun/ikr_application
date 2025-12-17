package com.nfirex.impl.data

import com.nfirex.impl.data.models.EmojiDto

internal class EmojiRepository(
    private val api: EmojiService
) {
    suspend fun searchEmojis(query: String): List<EmojiDto> {
        return api.searchEmojis(query)
    }
}