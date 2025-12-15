package com.example.ikr_application.alexcode69.domain

import com.example.ikr_application.nfirex.data.DeviceRepository
import java.util.Date

class CurrentDateUseCase() {
    fun date(): Date {
        val timestamp = DeviceRepository.INSTANCE.deviceInfo().currentTime
        val date = Date(timestamp)

        return date
    }
}