package com.argun.impl.domain

import com.argun.api.domain.ArgunTimePrecisions
import com.argun.api.domain.usecases.ArgunElapsedTimeUseCase
import com.argun.impl.data.ArgunRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class ArgunElapsedTimeUseCaseImpl(
    private val repository: ArgunRepository
) : ArgunElapsedTimeUseCase {
    override fun value(precision: ArgunTimePrecisions): Flow<Long> = flow {
        while (true) {
            val info = repository.argunInfo()
            val elapsedTime = info.elapsedTime
            emit(elapsedTime / precision.divider.inWholeMilliseconds)
            delay(100)
        }
    }.flowOn(Dispatchers.Default)
}

