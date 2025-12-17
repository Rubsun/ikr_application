package com.example.ikr_application.eremin.data

import com.example.ikr_application.eremin.data.models.CapybarasResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CapybaraApi {

    @GET("v1/capybaras")
    suspend fun getCapybaras(
        @Query("from") from: Int,
        @Query("take") take: Int
    ): CapybarasResponse
}
