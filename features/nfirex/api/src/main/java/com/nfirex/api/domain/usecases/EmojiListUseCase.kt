package com.nfirex.api.domain.usecases

import com.nfirex.api.domain.models.EmojiResult

interface EmojiListUseCase {
    suspend operator fun invoke(query: String): EmojiResult
}