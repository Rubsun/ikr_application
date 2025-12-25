package com.momuswinner.network.api.models

import kotlinx.serialization.Serializable

@Serializable
data class QuoteDto(
    val quote: String,
    val author: String
)