package com.example.ikr_application.alexcode69.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.alexcode69.domain.AddTimeEntryUseCase
import com.example.ikr_application.alexcode69.domain.CurrentDateUseCase
import com.example.ikr_application.alexcode69.domain.SearchTimeEntriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class MyViewModel : ViewModel() {
    private val currentDateUseCase = CurrentDateUseCase()
    private val searchTimeEntriesUseCase = SearchTimeEntriesUseCase()
    private val addTimeEntryUseCase = AddTimeEntryUseCase()

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
            kotlinx.coroutines.flow.combine(
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

    fun timePrecisions(): List<com.example.ikr_application.alexcode69.domain.TimePrecisions> {
        return com.example.ikr_application.alexcode69.domain.TimePrecisions.entries
    }
}

