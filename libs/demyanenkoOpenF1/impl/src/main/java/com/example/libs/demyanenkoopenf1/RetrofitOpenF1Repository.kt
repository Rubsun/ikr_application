package com.example.libs.demyanenkoopenf1

import com.example.libs.demyanenkoopenf1.api.OpenF1Api
import com.example.libs.demyanenkoopenf1.model.CarData
import com.example.libs.demyanenkoopenf1.model.Driver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Retrofit-based implementation of DemyanenkoOpenF1Repository.
 * Internal - features use only the interface.
 */
internal class RetrofitOpenF1Repository(
    private val api: OpenF1Api
) : DemyanenkoOpenF1Repository {

    override fun getDrivers(): Flow<List<Driver>> = flow {
        try {
            val drivers = api.getDrivers()
            emit(drivers)
        } catch (e: Exception) {
            e.printStackTrace()  // ✅ Log the error
            emit(emptyList())
        }
    }

    override fun getCarData(): Flow<List<CarData>> = flow {
        try {
            val carData = api.getCarData()
            emit(carData)
        } catch (e: Exception) {
            e.printStackTrace()  // ✅ Log the error
            emit(emptyList())
        }
    }
}
