package com.alexcode69.network.data

import com.alexcode69.network.data.models.RequestInfoResponseDto
import retrofit2.http.GET

/**
 * Retrofit интерфейс для работы с httpbin.org API.
 * Внутренняя реализация, не должна использоваться напрямую в feature модулях.
 */
internal interface RequestInfoService {
    @GET("get")
    suspend fun getRequestInfo(): RequestInfoResponseDto
}

