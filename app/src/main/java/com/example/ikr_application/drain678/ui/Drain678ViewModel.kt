package com.example.ikr_application.drain678.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.drain678.domain.AddTimeRecordUseCase
import com.example.ikr_application.drain678.domain.Drain678CurrentDateUseCase
import com.example.ikr_application.drain678.domain.Drain678ElapsedTimeUseCase
import com.example.ikr_application.drain678.domain.Drain678TimePrecisions
import com.example.ikr_application.drain678.domain.FilterTimeRecordsUseCase
import com.example.ikr_application.drain678.data.model.Drain678Info
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
class Drain678ViewModel : ViewModel() {
    private val currentDateUseCase = Drain678CurrentDateUseCase()
    private val elapsedTimeUseCase = Drain678ElapsedTimeUseCase()
    private val filterTimeRecordsUseCase = FilterTimeRecordsUseCase()
    private val addTimeRecordUseCase = AddTimeRecordUseCase()

    private val queryFlow = MutableStateFlow("")
    private val selectedPrecisionFlow = MutableStateFlow<Drain678TimePrecisions?>(null)
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

    fun timePrecisions(): List<Drain678TimePrecisions> {
        return Drain678TimePrecisions.entries
    }

    fun search(query: String) {
        queryFlow.value = query
    }

    fun selectPrecision(precision: Drain678TimePrecisions) {
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
        val records: List<Drain678Info> = emptyList(),
        val selectedPrecision: Drain678TimePrecisions? = null,
        val error: Throwable? = null,
    )
}