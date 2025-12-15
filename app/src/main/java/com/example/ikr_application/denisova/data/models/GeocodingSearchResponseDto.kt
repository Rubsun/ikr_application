package com.example.ikr_application.denisova.data.models

import kotlinx.serialization.Serializable

@Serializable
data class GeocodingSearchResponseDto(
    val results: List<GeocodingResultDto>? = null,
)

@Serializable
data class GeocodingResultDto(
    val id: Int? = null,
    val name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val country: String? = null,
    val admin1: String? = null,
)
