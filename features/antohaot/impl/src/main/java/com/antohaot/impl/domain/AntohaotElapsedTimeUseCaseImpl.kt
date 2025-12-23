package com.antohaot.impl.domain

import com.antohaot.api.domain.AntohaotTimePrecisions
import com.antohaot.api.domain.usecases.AntohaotElapsedTimeUseCase
import com.antohaot.impl.data.AntohaotRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class AntohaotElapsedTimeUseCaseImpl(
    private val repository: AntohaotRepository
) : AntohaotElapsedTimeUseCase {
    override fun value(precision: AntohaotTimePrecisions): Flow<Long> = flow {
        while (true) {
            val elapsedTime = repository.antohaotInfo().elapsedTime
            emit(elapsedTime / precision.divider.inWholeMilliseconds)
            delay(100)
        }
    }.flowOn(Dispatchers.Default)
}

