package com.artemkaa.network.data

import com.artemkaa.network.api.TimeApiClient
import com.artemkaa.network.api.models.TimeDto

/**
 * Реализация TimeApiClient через Retrofit.
 * Инкапсулирует всю логику работы с Retrofit и сетью.
 */
internal class RetrofitTimeApiClient(
    private val service: TimeService
) : TimeApiClient {
    override suspend fun getCurrentTime(timezone: String): TimeDto {
        val response = service.getCurrentTime(timezone)
        return TimeDto(
            datetime = response.datetime,
            timezone = response.timezone,
            unixtime = response.unixtime
        )
    }
}

