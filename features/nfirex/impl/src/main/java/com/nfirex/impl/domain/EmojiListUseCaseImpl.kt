package com.nfirex.impl.domain

import com.nfirex.impl.data.EmojiRepository
import com.nfirex.impl.data.models.EmojiDto
import com.nfirex.api.domain.models.Emoji
import com.nfirex.api.domain.usecases.EmojiListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Result.Companion.success

internal class EmojiListUseCaseImpl(
    private val repository: EmojiRepository
) : EmojiListUseCase {
    override suspend fun invoke(query: String): Result<List<Emoji>> = withContext(Dispatchers.IO) {
        if (query.isBlank()) {
            return@withContext success(emptyList())
        }

        runCatching {
            repository.searchEmojis(query)
                .map(::mapEmoji)
        }

    }

    private fun mapEmoji(dto: EmojiDto): Emoji {
        return Emoji(
            name = dto.name,
            codes = dto.htmlCode
        )
    }
}