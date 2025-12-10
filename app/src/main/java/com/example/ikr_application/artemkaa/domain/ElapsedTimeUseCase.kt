package com.example.ikr_application.artemkaa.domain

import com.example.ikr_application.nfirex.domain.TimePrecisions
import com.example.ikr_application.nfirex.data.DeviceRepository

class ElapsedTimeUseCase() {
    fun value(precisions: TimePrecisions): Long {
        val elapsedTime = DeviceRepository.INSTANCE.deviceInfo().elapsedTime

        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}