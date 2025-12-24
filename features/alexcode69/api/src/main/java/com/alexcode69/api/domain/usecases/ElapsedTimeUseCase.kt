package com.alexcode69.api.domain.usecases

import com.alexcode69.api.domain.models.TimePrecisions

interface ElapsedTimeUseCase {
    fun value(precisions: TimePrecisions): Long
}

