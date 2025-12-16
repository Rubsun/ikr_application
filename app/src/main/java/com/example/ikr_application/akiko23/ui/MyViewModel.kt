package com.example.ikr_application.akiko23.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.akiko23.domain.Akiko23CurrentDateUseCase
import com.example.ikr_application.akiko23.domain.Akiko23ElapsedTimeUseCase
import com.example.ikr_application.akiko23.domain.Akiko23TimePrecision

/**
 * ViewModel для экрана akiko23.
 * Работает с собственными use-case'ами и моделью времени.
 */
class Akiko23TimeViewModel : ViewModel() {
    private val currentDateUseCase = Akiko23CurrentDateUseCase()
    private val elapsedTimeUseCase = Akiko23ElapsedTimeUseCase()

    fun timePrecisions(): List<Akiko23TimePrecision> {
        return Akiko23TimePrecision.entries
    }

    fun date(): String {
        return currentDateUseCase.date().toString()
    }

    fun elapsedTime(precision: Akiko23TimePrecision): String {
        return "${elapsedTimeUseCase.value(precision)} ${precision.typeName}"
    }
}