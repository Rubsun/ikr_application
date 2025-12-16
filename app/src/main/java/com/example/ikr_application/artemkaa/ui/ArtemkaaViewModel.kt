package com.example.ikr_application.artemkaa.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.artemkaa.domain.ArtemkaaCurrentDateUseCase
import com.example.ikr_application.artemkaa.domain.ArtemkaaElapsedTimeUseCase
import com.example.ikr_application.artemkaa.domain.ArtemkaaTimePrecisions

class ArtemkaaViewModel : ViewModel() {
    private val currentDateUseCase = ArtemkaaCurrentDateUseCase()
    private val elapsedTimeUseCase = ArtemkaaElapsedTimeUseCase()

    fun timePrecisions(): List<ArtemkaaTimePrecisions> {
        return ArtemkaaTimePrecisions.entries
    }

    fun date(): String {
        return currentDateUseCase.date().toString()
    }

    fun elapsedTime(precision: ArtemkaaTimePrecisions): String {
        return "${elapsedTimeUseCase.value(precision)} ${precision.typeName}"
    }
}