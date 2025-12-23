package com.drain678.network.api

import com.drain678.network.api.models.TimeDto

/**
 * Клиент для работы с API времени.
 * Абстракция для получения данных о времени из внешнего источника.
 */
interface TimeApiClient {
    /**
     * Получение текущего времени по часовому поясу.
     *
     * @param timezone часовой пояс (например, "Europe/Moscow")
     * @return информация о текущем времени
     */
    suspend fun getCurrentTime(timezone: String = "UTC"): TimeDto
}

