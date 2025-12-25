package com.alexcode69.network.data

import com.alexcode69.network.api.Alexcode69ApiClient
import com.alexcode69.network.api.models.RequestInfoDto

/**
 * Реализация Alexcode69ApiClient через Retrofit.
 * Инкапсулирует всю логику работы с Retrofit и сетью.
 */
internal class RetrofitAlexcode69ApiClient(
    private val service: RequestInfoService
) : Alexcode69ApiClient {
    override suspend fun getRequestInfo(): RequestInfoDto {
        val response = service.getRequestInfo()
        return RequestInfoDto(
            url = response.url,
            origin = response.origin
        )
    }
}

