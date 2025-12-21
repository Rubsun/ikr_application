package com.rubsun.api.domain.usecases

import com.rubsun.api.domain.models.NumberDisplayModel
import kotlinx.coroutines.flow.Flow

interface GetNumberUseCase {
    suspend fun getRandomNumber(): NumberDisplayModel
    fun getAllNumbers(): Flow<List<NumberDisplayModel>>
    suspend fun addNumber(value: Int, label: String)
}


