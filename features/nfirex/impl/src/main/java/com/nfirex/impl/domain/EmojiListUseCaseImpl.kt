package com.nfirex.impl.domain

import com.nfirex.impl.data.EmojiRepository
import com.nfirex.impl.data.models.EmojiDto
import com.nfirex.api.domain.models.Emoji
import com.nfirex.api.domain.models.EmojiResult
import com.nfirex.api.domain.usecases.EmojiListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.Result.Companion.success

internal class EmojiListUseCaseImpl(
    private val repository: EmojiRepository
) : EmojiListUseCase {
    override suspend fun invoke(query: String): EmojiResult = withContext(Dispatchers.IO) {
        val result = when {
            query.isBlank() -> success(emptyList())

            else -> runCatching {
                repository.searchEmojis(query)
                    .map(::mapEmoji)
            }
        }

        if (result.getOrNull()?.isNotEmpty() == true) {
            launch {
                repository.saveSuggest(query)
            }
        }

        EmojiResult(
            query = query,
            items = result.getOrNull() ?: emptyList(),
            error = result.exceptionOrNull(),
        )
    }

    private fun mapEmoji(dto: EmojiDto): Emoji {
        return Emoji(
            name = dto.name,
            codes = dto.htmlCode
        )
    }
}