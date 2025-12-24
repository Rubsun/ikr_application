package com.n0tsszzz.network.data

import com.n0tsszzz.network.api.TimeApiClient
import com.n0tsszzz.network.api.models.TimeDto

/**
 * Реализация TimeApiClient через Retrofit.
 * Инкапсулирует всю логику работы с Retrofit и сетью.
 */
internal class RetrofitTimeApiClient(
    private val service: TimeService
) : TimeApiClient {
    override suspend fun getCurrentTime(): TimeDto {
        return service.getCurrentTime()
    }
}

