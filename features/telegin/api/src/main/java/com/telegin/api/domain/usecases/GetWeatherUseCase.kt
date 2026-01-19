package com.telegin.api.domain.usecases

import com.telegin.api.domain.models.Weather

interface GetWeatherUseCase {
    suspend operator fun invoke(city: String): Result<Weather>
}

