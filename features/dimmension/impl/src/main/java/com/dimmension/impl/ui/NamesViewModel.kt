package com.dimmension.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimmension.api.domain.models.NameDisplayModel
import com.dimmension.api.domain.usecases.AddNameUseCase
import com.dimmension.api.domain.usecases.FilterNamesUseCase
import com.dimmension.api.domain.usecases.GetRandomNameUseCase
import com.dimmension.api.domain.usecases.ObserveNamesUseCase
import com.example.injector.inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class NamesViewModel : ViewModel() {
    private val getRandomNameUseCase: GetRandomNameUseCase by inject()
    private val observeNamesUseCase: ObserveNamesUseCase by inject()
    private val addNameUseCase: AddNameUseCase by inject()
    private val filterNamesUseCase: FilterNamesUseCase by inject()

    private val searchQuery = MutableStateFlow("")
    private val randomName = MutableStateFlow<NameDisplayModel?>(null)
    private val isLoading = MutableStateFlow(false)

    private val names: StateFlow<List<NameDisplayModel>> = observeNamesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val uiState: StateFlow<NamesUiState> = combine(
        randomName,
        names,
        searchQuery,
        isLoading,
    ) { random, namesList, query, loading ->
        val filtered = filterNamesUseCase.invoke(namesList, query)

        NamesUiState(
            randomName = random,
            namesList = namesList,
            filteredNamesList = filtered,
            searchQuery = query,
            isLoading = loading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = NamesUiState()
    )

    init {
        refreshRandomName()
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun addName(firstName: String, lastName: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                addNameUseCase(firstName, lastName)
            } finally {
                isLoading.value = false
            }
        }
    }

    fun refreshRandomName() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                randomName.value = getRandomNameUseCase()
            } finally {
                isLoading.value = false
            }
        }
    }
}


