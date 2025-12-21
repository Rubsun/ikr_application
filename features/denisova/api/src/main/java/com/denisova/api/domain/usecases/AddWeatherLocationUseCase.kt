package com.denisova.api.domain.usecases

import com.denisova.api.domain.models.WeatherLocation

interface AddWeatherLocationUseCase {
    suspend fun execute(cityName: String): WeatherLocation
}
