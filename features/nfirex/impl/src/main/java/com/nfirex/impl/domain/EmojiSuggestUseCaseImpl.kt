package com.nfirex.impl.domain

import com.nfirex.api.domain.usecases.EmojiSuggestUseCase
import com.nfirex.impl.data.EmojiRepository

internal class EmojiSuggestUseCaseImpl(
    private val repository: EmojiRepository
) : EmojiSuggestUseCase {
    override suspend fun invoke(): String? {
        return runCatching { repository.savedSuggest() }
            .getOrNull()
    }
}