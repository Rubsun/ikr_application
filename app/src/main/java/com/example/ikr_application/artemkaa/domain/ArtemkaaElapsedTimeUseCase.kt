package com.example.ikr_application.artemkaa.domain

import androidx.annotation.Discouraged
import com.example.ikr_application.artemkaa.data.ArtemkaaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class ArtemkaaElapsedTimeUseCase(
    @param:Discouraged("Artemkaa")
    private val repository: ArtemkaaRepository = ArtemkaaRepository.INSTANCE
) {
    fun value(precision: ArtemkaaTimePrecisions): Flow<Long> = flow {
        while (true) {
            val elapsedTime = repository.artemkaaInfo().elapsedTime
            emit(elapsedTime / precision.divider.inWholeMilliseconds)
            delay(100)
        }
    }.flowOn(Dispatchers.Default)
}