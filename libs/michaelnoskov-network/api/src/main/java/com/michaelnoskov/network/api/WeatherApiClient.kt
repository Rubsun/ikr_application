package com.michaelnoskov.network.api

/**
 * Клиент для работы с API погоды.
 * Абстракция для получения данных о погоде из внешнего источника.
 */
interface WeatherApiClient {
    /**
     * Получает текущую температуру в указанной локации.
     *
     * @param latitude широта
     * @param longitude долгота
     * @return температура в градусах Цельсия
     */
    suspend fun getTemperature(latitude: Double, longitude: Double): Result<Double>
}

