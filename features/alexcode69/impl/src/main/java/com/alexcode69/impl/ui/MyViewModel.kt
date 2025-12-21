package com.alexcode69.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexcode69.api.domain.models.TimePrecisions
import com.alexcode69.api.domain.usecases.AddTimeEntryUseCase
import com.alexcode69.api.domain.usecases.CurrentDateUseCase
import com.alexcode69.api.domain.usecases.SearchTimeEntriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

internal class MyViewModel(
    private val currentDateUseCase: CurrentDateUseCase,
    private val searchTimeEntriesUseCase: SearchTimeEntriesUseCase,
    private val addTimeEntryUseCase: AddTimeEntryUseCase
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<TimerUiState> = _searchQuery
        .flatMapLatest { query ->
            Timber.d("Flatmap latest called with query: '$query'")
            searchTimeEntriesUseCase.search(query)
        }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        ).let { entriesStateFlow ->
            combine(
                entriesStateFlow,
                _searchQuery,
                _isLoading
            ) { entries, query, isLoading ->
                TimerUiState(
                    timeEntries = entries,
                    currentDate = currentDateUseCase.date().toString(),
                    searchQuery = query,
                    isLoading = isLoading,
                    error = null
                )
            }.stateIn(
                scope = viewModelScope,
                started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
                initialValue = TimerUiState()
            )
        }

    fun updateSearchQuery(query: String) {
        Timber.d("updateSearchQuery called with: '$query'")
        _searchQuery.value = query
    }

    fun addTimeEntry(label: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                Timber.d("Adding time entry: '$label'")
                addTimeEntryUseCase.execute(label)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun timePrecisions(): List<TimePrecisions> {
        return TimePrecisions.entries
    }
}

