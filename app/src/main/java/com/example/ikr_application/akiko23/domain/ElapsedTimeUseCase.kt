package com.example.ikr_application.akiko23.domain

import com.example.ikr_application.akiko23.data.DeviceRepository

class ElapsedTimeUseCase() {
    fun value(precisions: TimePrecisions): Long {
        val elapsedTime = DeviceRepository.INSTANCE.deviceInfo().elapsedTime

        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}
