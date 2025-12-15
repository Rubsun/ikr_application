package com.example.ikr_application.denisova.domain

import com.example.ikr_application.denisova.data.WeatherRepository
import com.example.ikr_application.denisova.domain.models.WeatherLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWeatherLocationsUseCase(
    private val repository: WeatherRepository = WeatherRepository
) {
    fun execute(): Flow<List<WeatherLocation>> = repository.locations.map { list ->
        list.map { dto ->
            WeatherLocation(
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
}
