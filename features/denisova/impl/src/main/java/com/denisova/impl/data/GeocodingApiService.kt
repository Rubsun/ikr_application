package com.denisova.impl.data

import com.denisova.impl.data.models.GeocodingSearchResponseDto

internal interface GeocodingApiService {
    suspend fun searchCity(
        name: String,
        count: Int = 1,
        language: String = "en",
        format: String = "json",
    ): GeocodingSearchResponseDto
}
