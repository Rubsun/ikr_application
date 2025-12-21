package com.alexcode69.impl.domain

import com.alexcode69.api.domain.models.TimePrecisions
import com.alexcode69.api.domain.usecases.ElapsedTimeUseCase
import com.alexcode69.impl.data.DeviceRepository

internal class ElapsedTimeUseCaseImpl(
    private val repository: DeviceRepository
) : ElapsedTimeUseCase {
    override fun value(precisions: TimePrecisions): Long {
        val elapsedTime = repository.deviceInfo().elapsedTime
        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}

