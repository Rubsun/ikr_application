package com.example.ikr_application.artemkaa.domain

import androidx.annotation.Discouraged
import com.example.ikr_application.artemkaa.data.ArtemkaaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

internal class ArtemkaaCurrentDateUseCase(
    @param:Discouraged("Artemkaa")
    private val repository: ArtemkaaRepository = ArtemkaaRepository.INSTANCE
) {
    fun date(): Flow<Date> = flow {
        while (true) {
            val timestamp = repository.artemkaaInfo().currentTime
            emit(Date(timestamp))
            delay(1000)
        }
    }.flowOn(Dispatchers.Default)
}