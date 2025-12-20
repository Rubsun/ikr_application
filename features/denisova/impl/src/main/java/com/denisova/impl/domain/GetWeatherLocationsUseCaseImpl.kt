package com.denisova.impl.domain

import com.denisova.api.domain.models.WeatherLocation
import com.denisova.api.domain.usecases.GetWeatherLocationsUseCase
import com.denisova.impl.data.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetWeatherLocationsUseCaseImpl(
    private val repository: WeatherRepository,
) : GetWeatherLocationsUseCase {
    override fun execute(): Flow<List<WeatherLocation>> = repository.locations.map { list ->
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
