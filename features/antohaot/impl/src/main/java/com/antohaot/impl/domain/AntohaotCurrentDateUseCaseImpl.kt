package com.antohaot.impl.domain

import com.antohaot.api.domain.usecases.AntohaotCurrentDateUseCase
import com.antohaot.impl.data.AntohaotRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

internal class AntohaotCurrentDateUseCaseImpl(
    private val repository: AntohaotRepository
) : AntohaotCurrentDateUseCase {
    override fun date(): Flow<Date> = flow {
        while (true) {
            val info = repository.antohaotInfo()
            val timestamp = info.currentTime
            emit(Date(timestamp))
            delay(1000)
        }
    }.flowOn(Dispatchers.Default)
}

