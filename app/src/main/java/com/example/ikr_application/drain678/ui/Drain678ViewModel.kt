package com.example.ikr_application.drain678.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.drain678.domain.Drain678CurrentDateUseCase
import com.example.ikr_application.drain678.domain.Drain678ElapsedTimeUseCase
import com.example.ikr_application.drain678.domain.Drain678TimePrecisions

class Drain678ViewModel : ViewModel() {
    private val currentDateUseCase = Drain678CurrentDateUseCase()
    private val elapsedTimeUseCase = Drain678ElapsedTimeUseCase()

    fun timePrecisions(): List<Drain678TimePrecisions> {
        return Drain678TimePrecisions.entries
    }

    fun date(): String {
        return currentDateUseCase.date().toString()
    }

    fun elapsedTime(precision: Drain678TimePrecisions): String {
        return "${elapsedTimeUseCase.value(precision)} ${precision.typeName}"
    }
}