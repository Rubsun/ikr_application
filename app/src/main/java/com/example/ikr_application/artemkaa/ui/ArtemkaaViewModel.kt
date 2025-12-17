package com.example.ikr_application.artemkaa.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.artemkaa.domain.AddTimeRecordUseCase
import com.example.ikr_application.artemkaa.domain.ArtemkaaCurrentDateUseCase
import com.example.ikr_application.artemkaa.domain.ArtemkaaElapsedTimeUseCase
import com.example.ikr_application.artemkaa.domain.ArtemkaaTimePrecisions
import com.example.ikr_application.artemkaa.domain.FilterTimeRecordsUseCase
import com.example.ikr_application.artemkaa.data.models.ArtemkaaInfo
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
class ArtemkaaViewModel : ViewModel() {
    private val currentDateUseCase = ArtemkaaCurrentDateUseCase()
    private val elapsedTimeUseCase = ArtemkaaElapsedTimeUseCase()
    private val filterTimeRecordsUseCase = FilterTimeRecordsUseCase()
    private val addTimeRecordUseCase = AddTimeRecordUseCase()

    private val queryFlow = MutableStateFlow("")
    private val selectedPrecisionFlow = MutableStateFlow<ArtemkaaTimePrecisions?>(null)
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

    fun timePrecisions(): List<ArtemkaaTimePrecisions> {
        return ArtemkaaTimePrecisions.entries
    }

    fun search(query: String) {
        queryFlow.value = query
    }

    fun selectPrecision(precision: ArtemkaaTimePrecisions) {
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
        val records: List<ArtemkaaInfo> = emptyList(),
        val selectedPrecision: ArtemkaaTimePrecisions? = null,
        val error: Throwable? = null,
    )
}