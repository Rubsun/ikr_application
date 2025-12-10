package com.example.ikr_application.n0tsSzzz.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.n0tsSzzz.domain.CurrentDateUseCase
import com.example.ikr_application.n0tsSzzz.domain.ElapsedTimeUseCase
import com.example.ikr_application.n0tsSzzz.domain.TimePrecisions

class MyViewModel : ViewModel() {
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

