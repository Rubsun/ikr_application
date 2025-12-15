package com.example.ikr_application.denisova.data.models

data class WeatherLocationDto(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String?,
    val admin1: String?,
    val time: String,
    val temperatureC: Double,
    val hourlyTimes: List<String>,
    val hourlyTemperaturesC: List<Double>,
)
