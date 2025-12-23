package com.antohaot.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antohaot.api.domain.AntohaotTimePrecisions
import com.antohaot.api.domain.models.AntohaotInfo
import com.antohaot.api.domain.usecases.AddTimeRecordUseCase
import com.antohaot.api.domain.usecases.AntohaotCurrentDateUseCase
import com.antohaot.api.domain.usecases.AntohaotElapsedTimeUseCase
import com.antohaot.api.domain.usecases.FilterTimeRecordsUseCase
import com.example.injector.inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
internal class AntohaotViewModel : ViewModel() {
    private val currentDateUseCase: AntohaotCurrentDateUseCase by inject()
    private val elapsedTimeUseCase: AntohaotElapsedTimeUseCase by inject()
    private val filterTimeRecordsUseCase: FilterTimeRecordsUseCase by inject()
    private val addTimeRecordUseCase: AddTimeRecordUseCase by inject()

    private val queryFlow = MutableStateFlow("")
    private val selectedPrecisionFlow = MutableStateFlow<AntohaotTimePrecisions?>(null)
    private val errorFlow = MutableStateFlow<Throwable?>(null)

    private val dateState = currentDateUseCase.date()
        .map { it.toString() }

    private val recordsState = queryFlow
        .debounce(300.milliseconds)
        .flatMapLatest { query -> filterTimeRecordsUseCase.invoke(query) }
        .distinctUntilChanged()

    private val elapsedTimeState = selectedPrecisionFlow
        .flatMapLatest { precision ->
            if (precision != null) {
                elapsedTimeUseCase.value(precision)
                    .map { "$it ${precision.typeName}" }
            } else {
                kotlinx.coroutines.flow.flowOf("")
            }
        }

    private val state = combine(
        dateState,
        elapsedTimeState,
        recordsState,
        errorFlow
    ) { date, elapsedTime, records, error ->
        State(
            currentDate = date,
            elapsedTime = elapsedTime,
            records = records,
            selectedPrecision = selectedPrecisionFlow.value,
            error = error
        )
    }
        .distinctUntilChanged()

    fun state(): Flow<State> {
        return state
    }

    fun timePrecisions(): List<AntohaotTimePrecisions> {
        return AntohaotTimePrecisions.entries
    }

    fun search(query: String) {
        queryFlow.value = query
    }

    fun selectPrecision(precision: AntohaotTimePrecisions) {
        selectedPrecisionFlow.value = precision
    }

    fun addTimeRecord() {
        viewModelScope.launch {
            errorFlow.value = null
            addTimeRecordUseCase.invoke()
                .onFailure { error ->
                    errorFlow.value = error
                }
        }
    }

    data class State(
        val currentDate: String = "",
        val elapsedTime: String = "",
        val records: List<AntohaotInfo> = emptyList(),
        val selectedPrecision: AntohaotTimePrecisions? = null,
        val error: Throwable? = null,
    )
}

