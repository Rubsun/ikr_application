package com.example.ikr_application.eremin.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CapybarasResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: List<CapybaraData>
)
