package com.example.impl.domain

import com.example.impl.data.F1Car
import com.example.impl.data.F1CarRepository
import kotlinx.coroutines.flow.Flow

internal class GetF1CarUseCase(private val repository: F1CarRepository) {
    fun getF1Cars(searchQuery: String = ""): Flow<List<F1Car>> = repository.getF1CarsFlow(searchQuery)
    suspend fun addF1Car(name: String, sound: String? = null): F1Car = repository.addF1Car(name, sound)
}
