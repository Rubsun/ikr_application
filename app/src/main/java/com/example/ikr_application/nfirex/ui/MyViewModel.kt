package com.example.ikr_application.nfirex.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.nfirex.domain.CurrentDateUseCase
import com.example.ikr_application.nfirex.domain.ElapsedTimeUseCase
import com.example.ikr_application.nfirex.domain.TimePrecisions

@Deprecated("Use your own classes:)")
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