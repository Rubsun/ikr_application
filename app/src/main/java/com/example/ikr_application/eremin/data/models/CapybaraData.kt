package com.example.ikr_application.eremin.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CapybaraData(
    @SerialName("url") val url: String,
    @SerialName("index") val index: Int,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
    @SerialName("alt") val alt: String
)
