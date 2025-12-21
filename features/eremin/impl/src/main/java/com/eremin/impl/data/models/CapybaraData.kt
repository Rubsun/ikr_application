package com.eremin.impl.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CapybaraData(
    @SerialName("url") val url: String,
    @SerialName("index") val index: Int,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
    @SerialName("alt") val alt: String
)
