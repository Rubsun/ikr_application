package com.example.ikr_application.kristevt.domain

import android.icu.util.Calendar
import com.example.ikr_application.kristevt.data.DeviceRepository
import java.util.Date

class CurrentDateUseCase() {
    fun date(): Date {
        val timestamp = DeviceRepository.INSTANCE.deviceInfo().currentTime
        val date = Date(timestamp)


        return date
    }
}