package com.example.ikr_application.drain678.domain

import com.example.ikr_application.nfirex.data.DeviceRepository
import com.example.ikr_application.nfirex.domain.TimePrecisions

class ElapsedTimeUseCase() {
    fun value(precisions: TimePrecisions): Long {
        val elapsedTime = DeviceRepository.INSTANCE.deviceInfo().elapsedTime

        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}