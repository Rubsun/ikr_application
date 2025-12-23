package com.akiko23.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akiko23.api.domain.models.TimePrecision
import com.akiko23.api.domain.usecases.CurrentDateUseCase
import com.akiko23.api.domain.usecases.ElapsedTimeUseCase
import com.akiko23.impl.data.models.Akiko23State
import com.example.injector.inject
import com.example.primitivestorage.api.PrimitiveStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана akiko23.
 * Использует Flow и персистентное хранилище для сохранения состояния.
 */
internal class Akiko23ViewModel : ViewModel() {
    private val currentDateUseCase: CurrentDateUseCase by inject()
    private val elapsedTimeUseCase: ElapsedTimeUseCase by inject()
    private val storage: PrimitiveStorage<Akiko23State> by inject()

    private val precisionFlow = MutableStateFlow<TimePrecision>(TimePrecision.S)
    private val showCatFlow = MutableStateFlow(false)

    data class State(
        val headerText: String,
        val elapsedText: String,
        val selectedPrecision: TimePrecision,
        val availablePrecisions: List<TimePrecision>,
        val showCat: Boolean,
    )

    init {
        // Загружаем состояние из хранилища
        viewModelScope.launch {
            storage.get().first()?.let { state ->
                precisionFlow.value = state.toPrecision()
                showCatFlow.value = state.showCat
            }
        }
    }

    private val stateInternal: StateFlow<State> = combine(
        precisionFlow,
        showCatFlow,
    ) { precision, showCat ->
        val rawDate = currentDateUseCase.date().toString()
        val elapsed = "${elapsedTimeUseCase.value(precision)} ${precision.typeName}"

        // Сохраняем состояние в хранилище
        viewModelScope.launch {
            storage.put(
                Akiko23State.fromPrecision(precision, showCat)
            )
        }

        State(
            headerText = rawDate,
            elapsedText = elapsed,
            selectedPrecision = precision,
            availablePrecisions = TimePrecision.entries,
            showCat = showCat,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = State(
            headerText = "",
            elapsedText = "",
            selectedPrecision = TimePrecision.S,
            availablePrecisions = TimePrecision.entries,
            showCat = false,
        ),
    )

    fun state(): StateFlow<State> = stateInternal

    fun selectPrecision(precision: TimePrecision) {
        precisionFlow.value = precision
    }

    fun toggleCat(show: Boolean) {
        showCatFlow.value = show
    }
}

