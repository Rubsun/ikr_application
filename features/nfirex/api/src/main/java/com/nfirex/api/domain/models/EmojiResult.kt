package com.nfirex.api.domain.models

data class EmojiResult(
    val query: String,
    val items: List<Emoji> = emptyList(),
    val error: Throwable? = null,
)
