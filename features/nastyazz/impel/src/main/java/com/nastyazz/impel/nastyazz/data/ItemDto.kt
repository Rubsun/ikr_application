package com.nastyazz.impel.nastyazz.data

import kotlinx.serialization.Serializable

@Serializable
internal data class ItemSuggestDto(
    val query: String
)