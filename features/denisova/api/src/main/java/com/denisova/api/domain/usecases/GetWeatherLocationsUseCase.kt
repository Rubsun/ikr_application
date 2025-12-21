package com.denisova.api.domain.usecases

import com.denisova.api.domain.models.WeatherLocation
import kotlinx.coroutines.flow.Flow

interface GetWeatherLocationsUseCase {
    fun execute(): Flow<List<WeatherLocation>>
}
