package com.example.ikr_application.rubsun.domain

import com.example.ikr_application.rubsun.data.NumberRepository
import com.example.ikr_application.rubsun.domain.models.NumberDisplayModel

class GetNumberUseCase(
    private val repository: NumberRepository = NumberRepository()
) {
    fun getRandomNumber(): NumberDisplayModel {
        val numberData = repository.getRandomNumber()
        return NumberDisplayModel(
            value = numberData.value,
            label = numberData.label,
            squared = numberData.value * numberData.value
        )
    }

    fun getAllNumbers(): List<NumberDisplayModel> {
        return repository.getAllNumbers().map { numberData ->
            NumberDisplayModel(
                value = numberData.value,
                label = numberData.label,
                squared = numberData.value * numberData.value
            )
        }
    }
}

