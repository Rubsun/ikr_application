package com.rubsun.impl.domain

import com.rubsun.api.domain.models.NumberDisplayModel
import com.rubsun.api.domain.usecases.GetNumberUseCase
import com.rubsun.impl.data.NumberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetNumberUseCaseImpl(
    private val repository: NumberRepository
) : GetNumberUseCase {
    override suspend fun getRandomNumber(): NumberDisplayModel {
        val numberData = repository.getRandomNumber()
        return NumberDisplayModel(
            value = numberData.value,
            label = numberData.label,
            squared = numberData.value * numberData.value
        )
    }

    override fun getAllNumbers(): Flow<List<NumberDisplayModel>> {
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

    override suspend fun addNumber(value: Int, label: String) {
        repository.addNumber(value, label)
    }
}


