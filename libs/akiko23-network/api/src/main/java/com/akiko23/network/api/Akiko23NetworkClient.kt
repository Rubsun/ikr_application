package com.akiko23.network.api

import com.akiko23.network.api.models.QuoteDto

/**
 * Клиент для работы с внешним API.
 * Абстракция для получения данных из внешнего источника.
 */
interface Akiko23NetworkClient {
    /**
     * Получение случайной цитаты из удаленного источника.
     *
     * @return случайная цитата
     */
    suspend fun getRandomQuote(): QuoteDto
}

