package com.artemkaa.api.domain.usecases

import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ArtemkaaCurrentDateUseCase {
    fun date(): Flow<Date>
}

