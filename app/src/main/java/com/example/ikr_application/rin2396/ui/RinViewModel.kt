package com.example.ikr_application.rin2396.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.rin2396.domain.RinCurrentDateUseCase
import com.example.ikr_application.rin2396.domain.RinElapsedTimeUseCase
import com.example.ikr_application.rin2396.domain.RinTimePrecisions

class RinViewModel : ViewModel() {
    private val currentDateUseCase = RinCurrentDateUseCase()
    private val elapsedTimeUseCase = RinElapsedTimeUseCase()

    fun timePrecisions(): List<RinTimePrecisions> {
        return RinTimePrecisions.entries
    }

    fun date(): String {
        return currentDateUseCase.date().toString()
    }

    fun elapsedTime(precision: RinTimePrecisions): String {
        return "${elapsedTimeUseCase.value(precision)} ${precision.typeName}"
    }
}