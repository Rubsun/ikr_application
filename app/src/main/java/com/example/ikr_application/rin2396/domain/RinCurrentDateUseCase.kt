package com.example.ikr_application.rin2396.domain

import com.example.ikr_application.rin2396.data.RinRepository
import java.util.Date

class RinCurrentDateUseCase() {
    fun date(): Date {
        val timestamp = RinRepository.INSTANCE.rinInfo().currentTime
        val date = Date(timestamp)

        return date
    }
}