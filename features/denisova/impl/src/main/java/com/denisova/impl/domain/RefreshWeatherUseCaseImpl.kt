package com.denisova.impl.domain

import com.denisova.api.domain.usecases.RefreshWeatherUseCase
import com.denisova.impl.data.WeatherRepository

internal class RefreshWeatherUseCaseImpl(
    private val repository: WeatherRepository,
) : RefreshWeatherUseCase {
    override suspend fun execute() {
        repository.refreshAll()
    }
}
