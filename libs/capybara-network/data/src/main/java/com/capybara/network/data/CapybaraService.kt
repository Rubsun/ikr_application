package com.capybara.network.data

import com.capybara.network.api.models.CapybarasResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface CapybaraService {

    @GET("v1/capybaras")
    suspend fun getCapybaras(
        @Query("from") from: Int,
        @Query("take") take: Int
    ): CapybarasResponse
}
