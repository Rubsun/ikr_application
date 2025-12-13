package com.example.ikr_application.nfirex.domain

import com.example.ikr_application.nfirex.data.EmojiRepository
import com.example.ikr_application.nfirex.data.models.EmojiDto
import com.example.ikr_application.nfirex.domain.models.Emoji
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Result.Companion.success

internal class EmojiListUseCase(
    private val repository: EmojiRepository = EmojiRepository.INSTANCE
) {
    suspend operator fun invoke(query: String): Result<List<Emoji>> = withContext(Dispatchers.IO) {
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