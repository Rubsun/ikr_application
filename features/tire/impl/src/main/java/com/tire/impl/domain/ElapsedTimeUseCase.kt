package com.tire.impl.domain

import com.tire.api.domain.TimePrecisions
import com.tire.impl.data.DeviceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.tire.api.domain.usecases.ElapsedTimeUseCase

internal class ElapsedTimeUseCase(
    private val repository: DeviceRepository
) : ElapsedTimeUseCase {
    override suspend operator fun invoke(precision: TimePrecisions): Long = withContext(Dispatchers.IO) {
        val elapsedTime = repository.deviceInfo().elapsedTime
        elapsedTime / precision.divider.inWholeMilliseconds
    }
}
