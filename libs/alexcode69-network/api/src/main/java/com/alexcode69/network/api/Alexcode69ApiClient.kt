package com.alexcode69.network.api

import com.alexcode69.network.api.models.RequestInfoDto

/**
 * Клиент для работы с HTTP API.
 * Абстракция для получения данных из внешнего источника.
 */
interface Alexcode69ApiClient {
    /**
     * Получение информации о запросе.
     *
     * @return данные о запросе (URL и Origin)
     */
    suspend fun getRequestInfo(): RequestInfoDto
}

