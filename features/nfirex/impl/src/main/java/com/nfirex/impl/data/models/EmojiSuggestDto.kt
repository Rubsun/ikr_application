package com.nfirex.impl.data.models

import kotlinx.serialization.Serializable

@Serializable
data class EmojiSuggestDto(
    val query: String
)
