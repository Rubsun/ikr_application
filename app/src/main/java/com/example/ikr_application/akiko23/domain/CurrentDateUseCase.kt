package com.example.ikr_application.akiko23.domain

import com.example.ikr_application.akiko23.data.DeviceRepository
import java.util.Date

class CurrentDateUseCase() {
    fun date(): Date {
        val timestamp = DeviceRepository.INSTANCE.deviceInfo().currentTime
        val date = Date(timestamp)

        return date
    }
}
