package com.example.ikr_application.antohaot.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.antohaot.domain.AntohaotCurrentDateUseCase
import com.example.ikr_application.antohaot.domain.AntohaotElapsedTimeUseCase
import com.example.ikr_application.antohaot.domain.AntohaotTimePrecisions

class AntohaotViewModel : ViewModel() {
    private val currentDateUseCase = AntohaotCurrentDateUseCase()
    private val elapsedTimeUseCase = AntohaotElapsedTimeUseCase()

    fun timePrecisions(): List<AntohaotTimePrecisions> {
        return AntohaotTimePrecisions.entries
    }

    fun date(): String {
        return currentDateUseCase.date().toString()
    }

    fun elapsedTime(precision: AntohaotTimePrecisions): String {
        return "${elapsedTimeUseCase.value(precision)} ${precision.typeName}"
    }
}