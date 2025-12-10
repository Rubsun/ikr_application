package com.example.ikr_application.n0tsSzzz.domain

import com.example.ikr_application.n0tsSzzz.data.DeviceRepository

class ElapsedTimeUseCase() {
    fun value(precisions: TimePrecisions): Long {
        val elapsedTime = DeviceRepository.INSTANCE.deviceInfo().elapsedTime

        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}

