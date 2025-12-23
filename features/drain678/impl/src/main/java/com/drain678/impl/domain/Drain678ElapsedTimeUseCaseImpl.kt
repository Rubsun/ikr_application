package com.drain678.impl.domain

import com.drain678.api.domain.Drain678TimePrecisions
import com.drain678.api.domain.usecases.Drain678ElapsedTimeUseCase
import com.drain678.impl.data.Drain678Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class Drain678ElapsedTimeUseCaseImpl(
    private val repository: Drain678Repository
) : Drain678ElapsedTimeUseCase {
    override fun value(precision: Drain678TimePrecisions): Flow<Long> = flow {
        while (true) {
            val elapsedTime = repository.drain678Info().elapsedTime
            emit(elapsedTime / precision.divider.inWholeMilliseconds)
            delay(100)
        }
    }.flowOn(Dispatchers.Default)
}

