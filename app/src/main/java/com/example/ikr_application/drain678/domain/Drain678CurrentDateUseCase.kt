package com.example.ikr_application.drain678.domain

import androidx.annotation.Discouraged
import com.example.ikr_application.drain678.data.Drain678Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

internal class Drain678CurrentDateUseCase(
    @param:Discouraged("Drain678")
    private val repository: Drain678Repository = Drain678Repository.INSTANCE
) {
    fun date(): Flow<Date> = flow {
        while (true) {
            val timestamp = repository.drain678Info().currentTime
            emit(Date(timestamp))
            delay(1000)
        }
    }.flowOn(Dispatchers.Default)
}