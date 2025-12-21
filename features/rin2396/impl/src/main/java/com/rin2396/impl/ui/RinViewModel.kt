package com.rin2396.impl.rin2396.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rin2396.impl.rin2396.domain.RinAddTimeEntryUseCase
import com.rin2396.impl.rin2396.domain.RinCurrentDateUseCase
import com.rin2396.impl.rin2396.domain.RinElapsedTimeUseCase
import com.rin2396.impl.rin2396.domain.RinSearchTimeEntriesUseCase
import com.rin2396.impl.rin2396.domain.RinTimePrecisions
import com.rin2396.impl.rin2396.data.models.RinInfo
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

internal class RinViewModel(
    private val addEntry: RinAddTimeEntryUseCase,
    private val searchEntries: RinSearchTimeEntriesUseCase,
    private val elapsedTime: RinElapsedTimeUseCase,
    private val currentDate: RinCurrentDateUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _selectedPrecision = MutableStateFlow(RinTimePrecisions.S)
    private val _isLoading = MutableStateFlow(false)
    private val _currentDate = MutableStateFlow("")
    private val _elapsedTime = MutableStateFlow("")

    val timePrecisions = RinTimePrecisions.entries

    val uiState: StateFlow<RinUiState> = _searchQuery
        .flatMapLatest { query ->
            searchEntries.search(query)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        ).let { entriesFlow ->
            combine(
                combine(
                    entriesFlow,
                    _searchQuery,
                    _selectedPrecision,
                    _isLoading,
                    _currentDate
                ) { entries, query, precision, loading, date ->
                    Triple(entries, query, Triple(precision, loading, date))
                },
                _elapsedTime
            ) { first, elapsed ->
                val (entries, query, second) = first
                val (precision, loading, date) = second

                RinUiState(
                    timeEntries = entries,
                    currentDate = date,
                    elapsedTime = elapsed,
                    selectedPrecision = precision,
                    searchQuery = query,
                    isLoading = loading
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RinUiState()
        )

    init {
        loadCurrentDate()
        loadElapsedTime()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectPrecision(precision: RinTimePrecisions) {
        _selectedPrecision.value = precision
        loadElapsedTime()
    }

    fun addTimeEntry() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                addEntry.execute()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadCurrentDate() {
        _currentDate.value = currentDate.date()
    }

    private fun loadElapsedTime() {
        val value = elapsedTime.value(_selectedPrecision.value)
        _elapsedTime.value = "$value ${_selectedPrecision.value.typeName}"
    }
}
