package com.drain678.network.data

import com.drain678.network.data.models.WorldTimeResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit интерфейс для работы с World Time API.
 * Внутренняя реализация, не должна использоваться напрямую в feature модулях.
 */
internal interface TimeService {
    @GET("api/timezone/{timezone}")
    suspend fun getCurrentTime(
        @Path("timezone") timezone: String
    ): WorldTimeResponse
}

