package com.drain678.api.domain.usecases

import com.drain678.api.domain.Drain678TimePrecisions
import kotlinx.coroutines.flow.Flow

interface Drain678ElapsedTimeUseCase {
    fun value(precision: Drain678TimePrecisions): Flow<Long>
}

