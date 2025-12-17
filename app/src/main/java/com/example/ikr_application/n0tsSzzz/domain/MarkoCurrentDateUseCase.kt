package com.example.ikr_application.n0tsSzzz.domain

import android.annotation.SuppressLint
import com.example.ikr_application.n0tsSzzz.data.MarkoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

@SuppressLint("DiscouragedApi")
class MarkoCurrentDateUseCase(
    @param:SuppressLint("DiscouragedApi")
    private val repository: MarkoRepository = MarkoRepository.INSTANCE
) {
    fun date(): Flow<Date> = flow {
        val timestamp = repository.deviceInfo().currentTime
        val date = Date(timestamp)
        emit(date)
    }.flowOn(Dispatchers.Default)
}
