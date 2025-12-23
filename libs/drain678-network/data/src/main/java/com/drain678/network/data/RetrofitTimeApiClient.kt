package com.drain678.network.data

import com.drain678.network.api.TimeApiClient
import com.drain678.network.api.models.TimeDto

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

