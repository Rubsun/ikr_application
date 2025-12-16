package com.example.ikr_application.demyanenko.domain

import com.example.ikr_application.demyanenko.data.F1Car
import com.example.ikr_application.demyanenko.data.F1CarRepository
import kotlinx.coroutines.flow.Flow

class GetF1CarUseCase(
    private val repository: F1CarRepository
) {
    fun getF1Cars(searchQuery: String = ""): Flow<List<F1Car>> {
        return repository.getF1CarsFlow(searchQuery)
    }

    suspend fun addF1Car(name: String, sound: String? = null): F1Car {
        return repository.addF1Car(name, sound)
    }
}
