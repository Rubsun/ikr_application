package com.drain678.impl.domain

import com.drain678.api.domain.usecases.Drain678CurrentDateUseCase
import com.drain678.impl.data.Drain678Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

internal class Drain678CurrentDateUseCaseImpl(
    private val repository: Drain678Repository
) : Drain678CurrentDateUseCase {
    override fun date(): Flow<Date> = flow {
        while (true) {
            val timestamp = repository.drain678Info().currentTime
            emit(Date(timestamp))
            delay(1000)
        }
    }.flowOn(Dispatchers.Default)
}

