package com.n0tsszzz.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n0tsszzz.api.domain.models.MarkoInfo
import com.n0tsszzz.api.domain.models.MarkoTimePrecisions
import com.n0tsszzz.api.domain.usecases.AddTimeRecordUseCase
import com.n0tsszzz.api.domain.usecases.GetTimeRecordsUseCase
import com.n0tsszzz.api.domain.usecases.MarkoCurrentDateUseCase
import com.n0tsszzz.api.domain.usecases.MarkoElapsedTimeUseCase
import com.n0tsszzz.impl.data.MarkoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
internal class MarkoViewModel : ViewModel() {
    private val currentDateUseCase: MarkoCurrentDateUseCase by com.example.injector.inject()
    private val elapsedTimeUseCase: MarkoElapsedTimeUseCase by com.example.injector.inject()
    private val getTimeRecordsUseCase: GetTimeRecordsUseCase by com.example.injector.inject()
    private val addTimeRecordUseCase: AddTimeRecordUseCase by com.example.injector.inject()
    private val repository: MarkoRepository by com.example.injector.inject()
    private val queryFlow = MutableStateFlow("")
    private val selectedPrecisionFlow = MutableStateFlow<MarkoTimePrecisions?>(null)
    private val errorFlow = MutableStateFlow<Throwable?>(null)
    
    private val dateState = currentDateUseCase.date()
        .map { it.toString() }
    
    @OptIn(ExperimentalCoroutinesApi::class)
    private val recordsState = queryFlow
        .debounce(300.milliseconds)
        .flatMapLatest { query -> getTimeRecordsUseCase.invoke(query) }
        .distinctUntilChanged()
    
    @OptIn(ExperimentalCoroutinesApi::class)
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
    
    fun timePrecisions(): List<MarkoTimePrecisions> {
        return MarkoTimePrecisions.entries
    }
    
    fun search(query: String) {
        queryFlow.value = query
    }
    
    fun selectPrecision(precision: MarkoTimePrecisions) {
        selectedPrecisionFlow.value = precision
    }
    
    fun addTimeRecord() {
        viewModelScope.launch {
            errorFlow.value = null
            val result = addTimeRecordUseCase.invoke()
            if (result.isSuccess) {
                errorFlow.value = null
            } else {
                result.onFailure { error ->
                    errorFlow.value = error
                }
            }
        }
    }

    fun clearRecords() {
        repository.clearAllRecords()
    }
    
    data class State(
        val currentDate: String = "",
        val elapsedTime: String = "",
        val records: List<MarkoInfo> = emptyList(),
        val selectedPrecision: MarkoTimePrecisions? = null,
        val error: Throwable? = null,
    )
}

