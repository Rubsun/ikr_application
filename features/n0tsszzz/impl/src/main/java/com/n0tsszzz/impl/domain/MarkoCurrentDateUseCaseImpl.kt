package com.n0tsszzz.impl.domain

import com.n0tsszzz.api.domain.usecases.MarkoCurrentDateUseCase
import com.n0tsszzz.impl.data.MarkoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

internal class MarkoCurrentDateUseCaseImpl(
    private val repository: MarkoRepository
) : MarkoCurrentDateUseCase {
    override fun date(): Flow<Date> = flow {
        // Получаем время из интернета через API
        val info = repository.getCurrentTimeFromApi()
        val date = Date(info.currentTime)
        emit(date)
    }.flowOn(Dispatchers.IO)
}

