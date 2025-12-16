package com.example.ikr_application.antohaot.domain

import androidx.annotation.Discouraged
import com.example.ikr_application.antohaot.data.AntohaotRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

internal class AntohaotCurrentDateUseCase(
    @param:Discouraged("Antohaot")
    private val repository: AntohaotRepository = AntohaotRepository.INSTANCE
) {
    fun date(): Flow<Date> = flow {
        while (true) {
            val timestamp = repository.antohaotInfo().currentTime
            emit(Date(timestamp))
            delay(1000)
        }
    }.flowOn(Dispatchers.Default)
}