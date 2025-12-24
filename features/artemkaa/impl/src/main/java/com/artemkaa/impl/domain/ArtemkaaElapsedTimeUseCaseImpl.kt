package com.artemkaa.impl.domain

import com.artemkaa.api.domain.ArtemkaaTimePrecisions
import com.artemkaa.api.domain.usecases.ArtemkaaElapsedTimeUseCase
import com.artemkaa.impl.data.ArtemkaaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class ArtemkaaElapsedTimeUseCaseImpl(
    private val repository: ArtemkaaRepository
) : ArtemkaaElapsedTimeUseCase {
    override fun value(precision: ArtemkaaTimePrecisions): Flow<Long> = flow {
        while (true) {
            val info = repository.artemkaaInfo()
            val elapsedTime = info.elapsedTime
            emit(elapsedTime / precision.divider.inWholeMilliseconds)
            delay(100)
        }
    }.flowOn(Dispatchers.Default)
}

