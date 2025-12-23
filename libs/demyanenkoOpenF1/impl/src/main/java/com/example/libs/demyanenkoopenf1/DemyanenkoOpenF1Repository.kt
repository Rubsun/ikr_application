package com.example.libs.demyanenkoopenf1

import com.example.libs.demyanenkoopenf1.model.CarData
import com.example.libs.demyanenkoopenf1.model.Driver
import kotlinx.coroutines.flow.Flow

/**
 * Repository abstraction for OpenF1 API.
 * Features only see this interface, not Retrofit/OkHttp.
 */
interface DemyanenkoOpenF1Repository {
    /**
     * Get all drivers from OpenF1 API
     */
    fun getDrivers(): Flow<List<Driver>>

    /**
     * Get car data from OpenF1 API
     */
    fun getCarData(): Flow<List<CarData>>
}
