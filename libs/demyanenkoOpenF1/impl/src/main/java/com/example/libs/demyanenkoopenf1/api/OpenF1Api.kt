package com.example.libs.demyanenkoopenf1.api

import com.example.libs.demyanenkoopenf1.model.CarData
import com.example.libs.demyanenkoopenf1.model.Driver
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service for OpenF1 API
 * Internal - not exposed to features
 */
internal interface OpenF1Api {

    @GET("v1/drivers")
    suspend fun getDrivers(
        @Query("session_key") sessionKey: Long? = null
    ): List<Driver>

    @GET("v1/car_data")
    suspend fun getCarData(
        @Query("session_key") sessionKey: Long? = null,
        @Query("driver_number") driverNumber: Int? = null
    ): List<CarData>
}
