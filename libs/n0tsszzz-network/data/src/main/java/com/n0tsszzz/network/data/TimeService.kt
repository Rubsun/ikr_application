package com.n0tsszzz.network.data

import com.n0tsszzz.network.api.models.TimeDto
import retrofit2.http.GET

/**
 * Retrofit интерфейс для работы с WorldTimeAPI.
 * Внутренняя реализация, не должна использоваться напрямую в feature модулях.
 */
internal interface TimeService {
    @GET("api/timezone/Europe/Moscow")
    suspend fun getCurrentTime(): TimeDto
}

