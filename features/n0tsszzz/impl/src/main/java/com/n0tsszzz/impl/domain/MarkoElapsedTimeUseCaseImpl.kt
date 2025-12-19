package com.n0tsszzz.impl.domain

import com.n0tsszzz.api.domain.models.MarkoTimePrecisions
import com.n0tsszzz.api.domain.usecases.MarkoElapsedTimeUseCase
import com.n0tsszzz.impl.data.MarkoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class MarkoElapsedTimeUseCaseImpl(
    private val repository: MarkoRepository
) : MarkoElapsedTimeUseCase {
    override fun value(precision: MarkoTimePrecisions): Flow<Long> = flow {
        val elapsedTime = repository.deviceInfo().elapsedTime
        val value = elapsedTime / precision.divider.inWholeMilliseconds
        emit(value)
    }.flowOn(Dispatchers.Default)
}

