package com.example.ikr_application.tire.domain

import android.annotation.SuppressLint
import com.example.ikr_application.tire.data.DeviceRepository

class ElapsedTimeUseCase() {
    @SuppressLint("DiscouragedApi")
    fun value(precisions: TimePrecisions): Long {
        val elapsedTime = DeviceRepository.INSTANCE.deviceInfo().elapsedTime

        return elapsedTime / precisions.divider.inWholeMilliseconds
    }
}

