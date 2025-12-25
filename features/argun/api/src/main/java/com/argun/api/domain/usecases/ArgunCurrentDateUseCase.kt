package com.argun.api.domain.usecases

import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ArgunCurrentDateUseCase {
    fun date(): Flow<Date>
}

