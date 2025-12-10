package com.example.ikr_application.kristevt.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.kristevt.domain.CurrentDateUseCase
import com.example.ikr_application.kristevt.domain.ElapsedTimeUseCase
import com.example.ikr_application.kristevt.domain.TimePrecisions

class MyViewModel : ViewModel() {
    private val currentDateUseCase = CurrentDateUseCase()
    private val elapsedTimeUseCase = ElapsedTimeUseCase()
    fun date(): String {
        return currentDateUseCase.date().toString()
    }

    fun elapsed(precisions: TimePrecisions): String {
        return "${elapsedTimeUseCase.value(precisions)} ${precisions.typeName}"
    }
}