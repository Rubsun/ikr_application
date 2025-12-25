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
        val valueLong = numberData.value.toLong()
        return NumberDisplayModel(
            value = numberData.value,
            label = numberData.label,
            squared = valueLong * valueLong
        )
    }

    override fun getAllNumbers(): Flow<List<NumberDisplayModel>> {
        return repository.numbers.map { numbers ->
            numbers.map { numberData ->
                val valueLong = numberData.value.toLong()
                NumberDisplayModel(
                    value = numberData.value,
                    label = numberData.label,
                    squared = valueLong * valueLong
                )
            }
        }
    }

    override suspend fun addNumber(value: Int, label: String) {
        repository.addNumber(value, label)
    }

    override suspend fun addNumberWithFactFromApi(value: Int) {
        val numberData = repository.fetchNumberFactFromApi(value)
        repository.addNumber(numberData.value, numberData.label)
    }

    override suspend fun clearAllNumbers() {
        repository.clearAllNumbers()
    }
}


