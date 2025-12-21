package com.example.ikr_application.dimmension.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.dimmension.domain.AddNameUseCase
import com.example.ikr_application.dimmension.domain.GetMultipleNamesUseCase
import com.example.ikr_application.dimmension.domain.GetRandomNameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NamesViewModel : ViewModel() {
    private val getRandomNameUseCase = GetRandomNameUseCase()
    private val getMultipleNamesUseCase = GetMultipleNamesUseCase()
    private val addNameUseCase = AddNameUseCase()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _randomName = MutableStateFlow<com.example.ikr_application.dimmension.domain.models.NameDisplayModel?>(null)
    private val _namesList = MutableStateFlow<List<com.example.ikr_application.dimmension.domain.models.NameDisplayModel>>(emptyList())
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<NamesUiState> = combine(
        _randomName,
        _namesList,
        _searchQuery,
        _isLoading
    ) { randomName, namesList, searchQuery, isLoading ->
        // Фильтрация выполняется синхронно, так как это легкая операция
        val filteredList = if (searchQuery.isBlank()) {
            namesList
        } else {
            val lowerQuery = searchQuery.lowercase()
            namesList.filter { name ->
                name.displayName.lowercase().contains(lowerQuery) ||
                name.shortName.lowercase().contains(lowerQuery) ||
                name.initials.lowercase().contains(lowerQuery)
            }
        }
        
        NamesUiState(
            randomName = randomName,
            namesList = namesList,
            searchQuery = searchQuery,
            filteredNamesList = filteredList,
            isLoading = isLoading
        )
    }.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = NamesUiState()
    )

    init {
        loadRandomName()
        observeNamesList()
    }

    private fun loadRandomName() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _randomName.value = getRandomNameUseCase.execute()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun observeNamesList() {
        getMultipleNamesUseCase.execute()
            .onEach { names ->
                _namesList.value = names
            }
            .launchIn(viewModelScope)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addName(firstName: String, lastName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                addNameUseCase.execute(firstName, lastName)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshRandomName() {
        loadRandomName()
    }
}

