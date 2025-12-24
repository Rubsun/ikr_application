package com.n0tsszzz.network.data

import com.n0tsszzz.network.data.models.WorldTimeApiResponse
import retrofit2.http.GET

/**
 * Retrofit интерфейс для работы с HTTP World Clock API.
 * Внутренняя реализация, не должна использоваться напрямую в feature модулях.
 */
internal interface TimeService {
    @GET("utc/now")
    suspend fun getCurrentTime(): WorldTimeApiResponse
}

