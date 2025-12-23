package com.nfirex.api.domain.usecases

interface EmojiSuggestUseCase {
    suspend operator fun invoke(): String?
}