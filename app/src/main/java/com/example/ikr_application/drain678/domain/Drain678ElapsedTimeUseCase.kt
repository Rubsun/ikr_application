package com.example.ikr_application.drain678.domain

import androidx.annotation.Discouraged
import com.example.ikr_application.drain678.data.Drain678Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class Drain678ElapsedTimeUseCase(
    @param:Discouraged("Drain678")
    private val repository: Drain678Repository = Drain678Repository.INSTANCE
) {
    fun value(precision: Drain678TimePrecisions): Flow<Long> = flow {
        while (true) {
            val elapsedTime = repository.drain678Info().elapsedTime
            emit(elapsedTime / precision.divider.inWholeMilliseconds)
            delay(100)
        }
    }.flowOn(Dispatchers.Default)
}