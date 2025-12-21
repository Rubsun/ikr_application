package com.antohaot.api.domain.usecases

import com.antohaot.api.domain.AntohaotTimePrecisions
import kotlinx.coroutines.flow.Flow

interface AntohaotElapsedTimeUseCase {
    fun value(precision: AntohaotTimePrecisions): Flow<Long>
}

