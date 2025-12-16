package com.nfirex.api.domain.usecases

import com.nfirex.api.domain.models.Emoji

interface EmojiListUseCase {
    suspend operator fun invoke(query: String): Result<List<Emoji>>
}