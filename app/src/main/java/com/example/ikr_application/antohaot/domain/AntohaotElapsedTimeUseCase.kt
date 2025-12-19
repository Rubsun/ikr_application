package com.example.ikr_application.antohaot.domain

import androidx.annotation.Discouraged
import com.example.ikr_application.antohaot.data.AntohaotRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class AntohaotElapsedTimeUseCase(
    @param:Discouraged("Antohaot")
    private val repository: AntohaotRepository = AntohaotRepository.INSTANCE
) {
    fun value(precision: AntohaotTimePrecisions): Flow<Long> = flow {
        while (true) {
            val elapsedTime = repository.antohaotInfo().elapsedTime
            emit(elapsedTime / precision.divider.inWholeMilliseconds)
            delay(100)
        }
    }.flowOn(Dispatchers.Default)
}