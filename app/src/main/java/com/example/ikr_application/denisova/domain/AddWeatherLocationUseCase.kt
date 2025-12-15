package com.example.ikr_application.denisova.domain

import com.example.ikr_application.denisova.data.WeatherRepository
import com.example.ikr_application.denisova.domain.models.WeatherLocation

class AddWeatherLocationUseCase(
    private val repository: WeatherRepository = WeatherRepository
) {
    suspend fun execute(cityName: String): WeatherLocation {
        val dto = repository.addLocation(cityName = cityName)
        return WeatherLocation(
            id = dto.id,
            name = dto.name,
            latitude = dto.latitude,
            longitude = dto.longitude,
            country = dto.country,
            admin1 = dto.admin1,
            time = dto.time,
            temperatureC = dto.temperatureC,
            hourlyTimes = dto.hourlyTimes,
            hourlyTemperaturesC = dto.hourlyTemperaturesC,
        )
    }
}
