package com.example.ikr_application.akiko23.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.akiko23.domain.Akiko23CurrentDateUseCase
import com.example.ikr_application.akiko23.domain.Akiko23ElapsedTimeUseCase
import com.example.ikr_application.akiko23.domain.Akiko23TimePrecision
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel для экрана akiko23.
 * Возвращает данные во фрагмент в виде State-объекта и использует Flow.
 */
class Akiko23TimeViewModel : ViewModel() {
    private val currentDateUseCase = Akiko23CurrentDateUseCase()
    private val elapsedTimeUseCase = Akiko23ElapsedTimeUseCase()

    private val precisionFlow = MutableStateFlow(Akiko23TimePrecision.S)
    private val showCatFlow = MutableStateFlow(false)

    data class State(
        val headerText: String,
        val elapsedText: String,
        val selectedPrecision: Akiko23TimePrecision,
        val availablePrecisions: List<Akiko23TimePrecision>,
        val showCat: Boolean,
    )

    private val stateInternal: StateFlow<State> = combine(
        precisionFlow,
        showCatFlow,
    ) { precision, showCat ->
        val rawDate = currentDateUseCase.date().toString()
        val elapsed = "${elapsedTimeUseCase.value(precision)} ${precision.typeName}"

        State(
            headerText = rawDate,
            elapsedText = elapsed,
            selectedPrecision = precision,
            availablePrecisions = Akiko23TimePrecision.entries,
            showCat = showCat,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = State(
            headerText = "",
            elapsedText = "",
            selectedPrecision = Akiko23TimePrecision.S,
            availablePrecisions = Akiko23TimePrecision.entries,
            showCat = false,
        ),
    )

    fun state(): StateFlow<State> = stateInternal

    fun selectPrecision(precision: Akiko23TimePrecision) {
        precisionFlow.value = precision
    }

    fun toggleCat(show: Boolean) {
        showCatFlow.value = show
    }
}