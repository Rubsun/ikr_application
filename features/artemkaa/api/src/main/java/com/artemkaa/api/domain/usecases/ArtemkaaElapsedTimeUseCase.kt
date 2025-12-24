package com.artemkaa.api.domain.usecases

import com.artemkaa.api.domain.ArtemkaaTimePrecisions
import kotlinx.coroutines.flow.Flow

interface ArtemkaaElapsedTimeUseCase {
    fun value(precision: ArtemkaaTimePrecisions): Flow<Long>
}

