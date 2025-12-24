package com.artemkaa.impl.domain

import com.artemkaa.api.domain.models.ArtemkaaInfo
import com.artemkaa.api.domain.usecases.ArtemkaaCurrentDateUseCase
import com.artemkaa.impl.data.ArtemkaaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

internal class ArtemkaaCurrentDateUseCaseImpl(
    private val repository: ArtemkaaRepository
) : ArtemkaaCurrentDateUseCase {
    override fun date(): Flow<Date> = flow {
        while (true) {
            val info = repository.artemkaaInfo()
            val timestamp = info.currentTime
            emit(Date(timestamp))
            delay(1000)
        }
    }.flowOn(Dispatchers.Default)
}

