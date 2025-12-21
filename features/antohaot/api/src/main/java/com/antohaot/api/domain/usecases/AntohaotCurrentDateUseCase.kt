package com.antohaot.api.domain.usecases

import kotlinx.coroutines.flow.Flow
import java.util.Date

interface AntohaotCurrentDateUseCase {
    fun date(): Flow<Date>
}

