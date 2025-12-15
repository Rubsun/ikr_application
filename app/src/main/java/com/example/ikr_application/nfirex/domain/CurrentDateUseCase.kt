package com.example.ikr_application.nfirex.domain

import com.example.ikr_application.nfirex.data.DeviceRepository
import java.util.Date

@Deprecated("Use your own classes:)")
class CurrentDateUseCase() {
    fun date(): Date {
        val timestamp = DeviceRepository.INSTANCE.deviceInfo().currentTime
        val date = Date(timestamp)

        return date
    }
}