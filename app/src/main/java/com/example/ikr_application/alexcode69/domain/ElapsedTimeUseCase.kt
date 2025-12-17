package com.example.ikr_application.alexcode69.domain

import com.example.ikr_application.alexcode69.domain.TimePrecisions
import com.example.ikr_application.alexcode69.data.DeviceRepository

class ElapsedTimeUseCase() {
    fun value(precisions: TimePrecisions): Long {
        val elapsedTime = DeviceRepository.INSTANCE.deviceInfo().elapsedTime

        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}