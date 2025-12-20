package com.dyatlova.impl.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class DestinationData(
    val id: String,
    val title: String,
    val country: String,
    val imageUrl: String,
    val tags: List<String>,
)

