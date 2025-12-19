package com.n0tsszzz.api.domain.usecases

import com.n0tsszzz.api.domain.models.MarkoTimePrecisions
import kotlinx.coroutines.flow.Flow

interface MarkoElapsedTimeUseCase {
    fun value(precision: MarkoTimePrecisions): Flow<Long>
}

