package com.denisova.impl.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class GeocodingSearchResponseDto(
    val results: List<GeocodingResultDto>? = null,
)

@Serializable
internal data class GeocodingResultDto(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String? = null,
    val admin1: String? = null,
)
