package com.n0tsszzz.api.domain.usecases

import kotlinx.coroutines.flow.Flow
import java.util.Date

interface MarkoCurrentDateUseCase {
    fun date(): Flow<Date>
}

