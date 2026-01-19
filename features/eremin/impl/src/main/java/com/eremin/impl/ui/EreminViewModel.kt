package com.eremin.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eremin.api.domain.models.Capybara
import com.eremin.impl.domain.GetCapybarasUseCase
import com.example.primitivestorage.api.PrimitiveStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class EreminViewModel(
    private val getCapybarasUseCase: GetCapybarasUseCase,
    private val primitiveStorage: PrimitiveStorage<String>
) : ViewModel() {

    private val _allCapybaras = MutableStateFlow<List<Capybara>>(emptyList())
    private val _searchQuery = MutableStateFlow<String>("")
    private val _isLoading = MutableStateFlow<Boolean>(false)
    private val _error = MutableStateFlow<String?>(null)

    private val filteredCapybarasFlow = combine(_allCapybaras, _searchQuery) { allCapybaras, query ->
        if (query.isEmpty()) {
            allCapybaras
        } else {
            allCapybaras.filter { it.alt.contains(query, ignoreCase = true) }
        }
    }

    val uiState: StateFlow<EreminUiState> = combine(
        filteredCapybarasFlow,
        _searchQuery,
        _isLoading,
        _error
    ) { capybaras, searchQuery, isLoading, error ->
        EreminUiState(
            capybaras = capybaras,
            searchQuery = searchQuery,
            isLoading = isLoading,
            error = error
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = EreminUiState(isLoading = true)
    )

    init {
        loadSearchQuery()
        loadCapybaras()
    }

    private fun loadSearchQuery() {
        viewModelScope.launch {
            val savedQuery = primitiveStorage.get().first() ?: ""
            _searchQuery.value = savedQuery
        }
    }

    private fun loadCapybaras() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            getCapybarasUseCase.execute(0, 100)
                .catch { e ->
                    _error.value = e.message ?: "Ошибка загрузки капибар"
                    _isLoading.value = false
                }
                .onEach { capybaras ->
                    _allCapybaras.value = capybaras
                    _isLoading.value = false
                }
                .launchIn(viewModelScope)
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            primitiveStorage.put(query)
        }
    }
}
