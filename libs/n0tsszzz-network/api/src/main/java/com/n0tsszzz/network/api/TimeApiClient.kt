package com.n0tsszzz.network.api

import com.n0tsszzz.network.api.models.TimeDto

/**
 * Клиент для работы с API времени.
 * Абстракция для получения данных о времени из внешнего источника.
 */
interface TimeApiClient {
    /**
     * Получение текущего времени из API.
     *
     * @return данные о текущем времени
     */
    suspend fun getCurrentTime(): TimeDto
}

