package com.nfirex.impl.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class EmojiDto(
    val name: String,
    val category: String,
    val group: String,
    val htmlCode: List<String>,
    val unicode: List<String>
)
