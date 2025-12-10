package com.example.ikr_application.tire.domain

import android.annotation.SuppressLint
import com.example.ikr_application.tire.data.DeviceRepository
import java.util.Date

class CurrentDateUseCase() {
    @SuppressLint("DiscouragedApi")
    fun date(): Date {
        val timestamp = DeviceRepository.INSTANCE.deviceInfo().currentTime
        val date = Date(timestamp)

        return date
    }
}

