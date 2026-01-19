package com.telegin.network.api

import com.telegin.network.api.models.WeatherDto

/**
 * Клиент для работы с API погоды.
 * Абстракция для получения данных о погоде из внешнего источника.
 */
interface WeatherApiClient {
    /**
     * Получение погоды по городу.
     *
     * @param city название города
     * @return данные о погоде
     */
    suspend fun getWeather(city: String): WeatherDto
}

