package com.example.ikr_application.stupishin.domain

import com.example.ikr_application.stupishin.data.DeviceRepository
import java.util.Date

class CurrentDateUseCase() {
    private val repo = DeviceRepository()

    fun date(): Date {
        val timestamp = repo.deviceInfo().currentTime
        val date = Date(timestamp)

        return date
    }
}
