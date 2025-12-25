package com.rin2396.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rin2396.api.domain.models.CatModel
import com.rin2396.api.domain.usecases.AddCatUseCase
import com.rin2396.api.domain.usecases.GetCatsUseCase
import com.rin2396.api.domain.usecases.SearchCatsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

internal data class CatsUiState(
    val cats: List<CatModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)

internal class CatsViewModel(
    private val getCatsUseCase: GetCatsUseCase,
    private val addCatUseCase: AddCatUseCase,
    private val searchCatsUseCase: SearchCatsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CatsUiState())
    val uiState: StateFlow<CatsUiState> = _uiState.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            combine(
                getCatsUseCase.execute(),
                searchQuery
            ) { cats, query ->
                if (query.isEmpty()) {
                    cats
                } else {
                    cats.filter { it.tag.contains(query, ignoreCase = true) }
                }
            }.collect { filteredCats ->
                _uiState.value = _uiState.value.copy(cats = filteredCats)
            }
        }
    }

    fun addCat(tag: String = "random") {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            addCatUseCase.execute(tag)
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
                .collect {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }
}
