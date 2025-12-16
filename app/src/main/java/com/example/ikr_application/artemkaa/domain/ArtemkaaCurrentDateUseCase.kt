package com.example.ikr_application.artemkaa.domain

import com.example.ikr_application.artemkaa.data.ArtemkaaRepository
import java.util.Date

class ArtemkaaCurrentDateUseCase() {
    fun date(): Date {
        val timestamp = ArtemkaaRepository.INSTANCE.artemkaaInfo().currentTime
        val date = Date(timestamp)

        return date
    }
}