package com.example.ikr_application.n0tsSzzz.domain

import com.example.ikr_application.n0tsSzzz.data.DeviceRepository
import java.util.Date

class CurrentDateUseCase() {
    fun date(): Date {
        val timestamp = DeviceRepository.INSTANCE.deviceInfo().currentTime
        val date = Date(timestamp)

        return date
    }
}

