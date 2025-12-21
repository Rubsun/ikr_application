package com.denisova.impl.data

import com.denisova.impl.data.models.GeocodingSearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface GeocodingApiService {
    @GET("v1/search")
    suspend fun searchCity(
        @Query("name") name: String,
        @Query("count") count: Int = 1,
        @Query("language") language: String = "en",
        @Query("format") format: String = "json",
    ): GeocodingSearchResponseDto
}
