package com.example.ikr_application.spl3g.domain

import com.example.ikr_application.spl3g.data.DeviceRepository
import java.util.Date

class CurrentDateUseCase() {
    fun date(): Date {
        val timestamp = DeviceRepository.INSTANCE.deviceInfo().currentTime
        val date = Date(timestamp)

        return date
    }
}
