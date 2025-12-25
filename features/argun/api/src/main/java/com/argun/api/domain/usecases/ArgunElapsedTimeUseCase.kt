package com.argun.api.domain.usecases

import com.argun.api.domain.ArgunTimePrecisions
import kotlinx.coroutines.flow.Flow

interface ArgunElapsedTimeUseCase {
    fun value(precision: ArgunTimePrecisions): Flow<Long>
}

