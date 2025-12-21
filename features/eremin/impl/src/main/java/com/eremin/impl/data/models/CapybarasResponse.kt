package com.eremin.impl.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CapybarasResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: List<CapybaraData>
)
