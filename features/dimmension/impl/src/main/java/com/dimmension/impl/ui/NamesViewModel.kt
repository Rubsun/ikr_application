package com.dimmension.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimmension.api.domain.models.NameDisplayModel
import com.dimmension.api.domain.usecases.AddNameUseCase
import com.dimmension.api.domain.usecases.FetchRandomNamesFromNetworkUseCase
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
    private val fetchRandomNamesFromNetworkUseCase: FetchRandomNamesFromNetworkUseCase by inject()

    private val searchQuery = MutableStateFlow("")
    private val randomName = MutableStateFlow<NameDisplayModel?>(null)
    private val isLoading = MutableStateFlow(false)
    private val networkError = MutableStateFlow<String?>(null)
    private val lastFetchedFromNetwork = MutableStateFlow<List<NameDisplayModel>>(emptyList())

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
        combine(isLoading, networkError, lastFetchedFromNetwork) { loading, error, fetched ->
            Triple(loading, error, fetched)
        }
    ) { random, namesList, query, (loading, error, fetched) ->
        val filtered = filterNamesUseCase.invoke(namesList, query)

        NamesUiState(
            randomName = random,
            namesList = namesList,
            filteredNamesList = filtered,
            searchQuery = query,
            isLoading = loading,
            networkError = error,
            lastFetchedFromNetwork = fetched
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

    /**
     * Загружает случайные имена из интернета
     * @param count количество имён для загрузки
     */
    fun fetchNamesFromNetwork(count: Int = 3) {
        viewModelScope.launch {
            isLoading.value = true
            networkError.value = null
            try {
                fetchRandomNamesFromNetworkUseCase(count)
                    .onSuccess { names ->
                        lastFetchedFromNetwork.value = names
                    }
                    .onFailure { throwable ->
                        networkError.value = throwable.message ?: "Ошибка загрузки из сети"
                    }
            } finally {
                isLoading.value = false
            }
        }
    }

    /**
     * Очищает сообщение об ошибке сети
     */
    fun clearNetworkError() {
        networkError.value = null
    }
}


