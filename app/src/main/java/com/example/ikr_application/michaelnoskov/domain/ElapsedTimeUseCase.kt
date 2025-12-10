package com.example.ikr_application.michaelnoskov.domain

import com.example.ikr_application.nfirex.data.DeviceRepository

class ElapsedTimeUseCase() {
    fun value(precisions: TimePrecisions): Long {
        val elapsedTime = DeviceRepository.INSTANCE.deviceInfo().elapsedTime

        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}