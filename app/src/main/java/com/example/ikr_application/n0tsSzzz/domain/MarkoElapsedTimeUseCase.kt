package com.example.ikr_application.n0tsSzzz.domain

import android.annotation.SuppressLint
import com.example.ikr_application.n0tsSzzz.data.MarkoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@SuppressLint("DiscouragedApi")
class MarkoElapsedTimeUseCase(
    @param:SuppressLint("DiscouragedApi")
    private val repository: MarkoRepository = MarkoRepository.INSTANCE
) {
    fun value(precision: MarkoTimePrecisions): Flow<Long> = flow {
        val elapsedTime = repository.deviceInfo().elapsedTime
        val value = elapsedTime / precision.divider.inWholeMilliseconds
        emit(value)
    }.flowOn(Dispatchers.Default)
}
