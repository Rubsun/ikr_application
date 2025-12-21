package com.denisova.impl.domain

import com.denisova.api.domain.models.WeatherLocation
import com.denisova.api.domain.usecases.AddWeatherLocationUseCase
import com.denisova.impl.data.WeatherRepository

internal class AddWeatherLocationUseCaseImpl(
    private val repository: WeatherRepository,
) : AddWeatherLocationUseCase {
    override suspend fun execute(cityName: String): WeatherLocation {
        val dto = repository.addLocation(cityName)
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
