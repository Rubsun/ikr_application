package com.example.ikr_application.denisova.domain

import com.example.ikr_application.denisova.data.WeatherRepository

class RefreshWeatherUseCase(
    private val repository: WeatherRepository = WeatherRepository
) {
    suspend fun execute() {
        repository.refreshAll()
    }
}
