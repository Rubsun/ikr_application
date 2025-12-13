package com.example.ikr_application.nfirex.domain

import com.example.ikr_application.nfirex.data.DeviceRepository

@Deprecated("Use your own classes:)")
class ElapsedTimeUseCase() {
    fun value(precisions: TimePrecisions): Long {
        val elapsedTime = DeviceRepository.INSTANCE.deviceInfo().elapsedTime

        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}