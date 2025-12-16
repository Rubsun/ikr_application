package com.example.ikr_application.antohaot.domain

import com.example.ikr_application.antohaot.data.AntohaotRepository
import java.util.Date

class AntohaotCurrentDateUseCase() {
    fun date(): Date {
        val timestamp = AntohaotRepository.INSTANCE.antohaotInfo().currentTime
        val date = Date(timestamp)

        return date
    }
}