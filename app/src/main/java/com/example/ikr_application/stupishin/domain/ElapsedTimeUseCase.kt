package com.example.ikr_application.stupishin.domain

import com.example.ikr_application.stupishin.data.DeviceRepository

class ElapsedTimeUseCase() {
    private val repo = DeviceRepository()

    fun value(precisions: TimePrecisions): Long {
        val elapsedTime = repo.deviceInfo().elapsedTime

        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}
