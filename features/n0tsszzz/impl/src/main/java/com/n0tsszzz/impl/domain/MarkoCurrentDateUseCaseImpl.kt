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
        val timestamp = repository.deviceInfo().currentTime
        val date = Date(timestamp)
        emit(date)
    }.flowOn(Dispatchers.Default)
}

