package com.drain678.api.domain.usecases

import kotlinx.coroutines.flow.Flow
import java.util.Date

interface Drain678CurrentDateUseCase {
    fun date(): Flow<Date>
}

