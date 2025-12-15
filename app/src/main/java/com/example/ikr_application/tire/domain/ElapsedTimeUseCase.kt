package com.example.ikr_application.tire.domain

import com.example.ikr_application.tire.data.DeviceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElapsedTimeUseCase(
    private val repository: DeviceRepository = DeviceRepository.INSTANCE
) {
    suspend operator fun invoke(precision: TimePrecisions): Long = withContext(Dispatchers.IO) {
        val elapsedTime = repository.deviceInfo().elapsedTime
        elapsedTime / precision.divider.inWholeMilliseconds
    }
}

