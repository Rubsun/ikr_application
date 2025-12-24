package com.tire.api.domain.usecases

import com.tire.api.domain.TimePrecisions

interface ElapsedTimeUseCase{
    suspend operator fun invoke(precision: TimePrecisions): Long
}
