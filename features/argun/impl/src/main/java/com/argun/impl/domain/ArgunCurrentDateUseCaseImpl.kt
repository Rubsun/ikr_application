package com.argun.impl.domain

import com.argun.api.domain.models.ArgunInfo
import com.argun.api.domain.usecases.ArgunCurrentDateUseCase
import com.argun.impl.data.ArgunRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

internal class ArgunCurrentDateUseCaseImpl(
    private val repository: ArgunRepository
) : ArgunCurrentDateUseCase {
    override fun date(): Flow<Date> = flow {
        while (true) {
            val info = repository.argunInfo()
            val timestamp = info.currentTime
            emit(Date(timestamp))
            delay(1000)
        }
    }.flowOn(Dispatchers.Default)
}

