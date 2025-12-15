package com.example.ikr_application.n0tsSzzz.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.n0tsSzzz.domain.MarkoCurrentDateUseCase
import com.example.ikr_application.n0tsSzzz.domain.MarkoElapsedTimeUseCase
import com.example.ikr_application.n0tsSzzz.domain.MarkoTimePrecisions

class MarkoViewModel : ViewModel() {
    private val currentDateUseCase = MarkoCurrentDateUseCase()
    private val elapsedTimeUseCase = MarkoElapsedTimeUseCase()

    fun timePrecisions(): List<MarkoTimePrecisions> {
        return MarkoTimePrecisions.entries
    }

    fun date(): String {
        return currentDateUseCase.date().toString()
    }

    fun elapsedTime(precision: MarkoTimePrecisions): String {
        return "${elapsedTimeUseCase.value(precision)} ${precision.typeName}"
    }
}

