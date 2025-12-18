package com.example.ikr_application.rin2396.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.rin2396.domain.RinAddTimeEntryUseCase
import com.example.ikr_application.rin2396.domain.RinCurrentDateUseCase
import com.example.ikr_application.rin2396.domain.RinElapsedTimeUseCase
import com.example.ikr_application.rin2396.domain.RinSearchTimeEntriesUseCase
import com.example.ikr_application.rin2396.domain.RinTimePrecisions
import com.example.ikr_application.rin2396.data.models.RinInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

data class RinUiState(
    val timeEntries: List<RinInfo> = emptyList(),
    val currentDate: String = "",
    val elapsedTime: String = "",
    val selectedPrecision: RinTimePrecisions = RinTimePrecisions.S,
    val searchQuery: String = "",
    val isLoading: Boolean = false,
)

class RinViewModel : ViewModel() {
    private val currentDateUseCase = RinCurrentDateUseCase()
    private val elapsedTimeUseCase = RinElapsedTimeUseCase()
    private val searchTimeEntriesUseCase = RinSearchTimeEntriesUseCase()
    private val addTimeEntryUseCase = RinAddTimeEntryUseCase()

    private val _searchQuery = MutableStateFlow("")
    private val _selectedPrecision = MutableStateFlow(RinTimePrecisions.S)
    private val _isLoading = MutableStateFlow(false)
    private val _currentDate = MutableStateFlow("")
    private val _elapsedTime = MutableStateFlow("")

    val uiState: StateFlow<RinUiState> = _searchQuery
        .flatMapLatest { query ->
            Timber.d("Search query changed: '$query'")
            searchTimeEntriesUseCase.search(query)
        }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        ).let { entriesStateFlow ->
            combine(
                combine(
                    entriesStateFlow,
                    _searchQuery,
                    _selectedPrecision,
                    _isLoading,
                    _currentDate
                ) { entries: List<RinInfo>, query: String, precision: RinTimePrecisions, isLoading: Boolean, date: String ->
                    Triple(entries, query, Triple(precision, isLoading, date))
                },
                _elapsedTime
            ) { firstPart, elapsed: String ->
                val (entries, query, secondPart) = firstPart
                val (precision, isLoading, date) = secondPart
                RinUiState(
                    timeEntries = entries,
                    currentDate = date,
                    elapsedTime = elapsed,
                    selectedPrecision = precision,
                    searchQuery = query,
                    isLoading = isLoading
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = RinUiState()
            )
        }

    init {
        loadCurrentDate()
        loadElapsedTime()
    }

    fun timePrecisions(): List<RinTimePrecisions> {
        return RinTimePrecisions.entries
    }

    fun updateSearchQuery(query: String) {
        Timber.d("updateSearchQuery called with: '$query'")
        _searchQuery.value = query
    }

    fun selectPrecision(precision: RinTimePrecisions) {
        _selectedPrecision.value = precision
        loadElapsedTime()
    }

    fun addTimeEntry() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                Timber.d("Adding time entry")
                addTimeEntryUseCase.execute()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadCurrentDate() {
        viewModelScope.launch {
            val date = currentDateUseCase.date()
            _currentDate.value = date
        }
    }

    private fun loadElapsedTime() {
        viewModelScope.launch {
            val value = elapsedTimeUseCase.value(_selectedPrecision.value)
            _elapsedTime.value = "$value ${_selectedPrecision.value.typeName}"
        }
    }
}