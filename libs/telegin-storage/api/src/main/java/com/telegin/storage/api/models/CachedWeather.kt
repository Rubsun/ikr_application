package com.telegin.storage.api.models

import kotlinx.serialization.Serializable

@Serializable
data class CachedWeather(
    val city: String,
    val temperature: Double,
    val description: String,
    val humidity: Int,
    val windSpeed: Double,
    val iconUrl: String?,
    val timestamp: Long = System.currentTimeMillis()
)

