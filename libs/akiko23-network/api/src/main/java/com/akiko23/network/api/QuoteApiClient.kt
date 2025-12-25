package com.akiko23.network.api

import com.akiko23.network.api.models.QuoteDto

/**
 * Клиент для работы с API цитат.
 * Абстракция для получения цитат из внешнего источника.
 */
interface QuoteApiClient {
    /**
     * Получить случайную цитату.
     *
     * @return случайная цитата
     */
    suspend fun getRandomQuote(): QuoteDto
}

