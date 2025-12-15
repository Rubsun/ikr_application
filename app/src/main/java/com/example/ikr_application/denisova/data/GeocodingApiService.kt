package com.example.ikr_application.denisova.data

import com.example.ikr_application.denisova.data.models.GeocodingSearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {
    @GET("v1/search")
    suspend fun searchCity(
        @Query("name") name: String,
        @Query("count") count: Int = 1,
        @Query("language") language: String = "en",
        @Query("format") format: String = "json",
    ): GeocodingSearchResponseDto
}
