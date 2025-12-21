package com.eremin.impl.data

import com.eremin.impl.data.models.CapybarasResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface CapybaraApi {

    @GET("v1/capybaras")
    suspend fun getCapybaras(
        @Query("from") from: Int,
        @Query("take") take: Int
    ): CapybarasResponse
}
