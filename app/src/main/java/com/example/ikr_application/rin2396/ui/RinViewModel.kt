package com.example.ikr_application.rin2396.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.rin2396.domain.CurrentDateUseCase
import com.example.ikr_application.rin2396.domain.ElapsedTimeUseCase
import com.example.ikr_application.rin2396.domain.TimePrecisions

class RinViewModel : ViewModel() {
    private val currentDateUseCase = CurrentDateUseCase()
    private val elapsedTimeUseCase = ElapsedTimeUseCase()

    fun timePrecisions(): List<TimePrecisions> {
        return TimePrecisions.entries
    }

    fun date(): String {
        return currentDateUseCase.date().toString()
    }

    fun elapsedTime(precision: TimePrecisions): String {
        return "${elapsedTimeUseCase.value(precision)} ${precision.typeName}"
    }
}