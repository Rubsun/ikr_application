package com.example.ikr_application.rubsun.domain

import com.example.ikr_application.rubsun.data.NumberRepository
import com.example.ikr_application.rubsun.domain.models.NumberDisplayModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNumberUseCase(
    private val repository: NumberRepository = NumberRepository()
) {
    suspend fun getRandomNumber(): NumberDisplayModel {
        val numberData = repository.getRandomNumber()
        return NumberDisplayModel(
            value = numberData.value,
            label = numberData.label,
            squared = numberData.value * numberData.value
        )
    }

    fun getAllNumbers(): Flow<List<NumberDisplayModel>> {
        return repository.numbers.map { numbers ->
            numbers.map { numberData ->
                NumberDisplayModel(
                    value = numberData.value,
                    label = numberData.label,
                    squared = numberData.value * numberData.value
                )
            }
        }
    }

    suspend fun addNumber(value: Int, label: String) {
        repository.addNumber(value, label)
    }
}
