package com.tire.impl.ui.cases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.injector.inject
import com.tire.impl.ui.states.CasesUiState
import com.tire.api.domain.usecases.GetAllCasesUseCase
import com.tire.api.domain.usecases.OpenCaseUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

internal class CasesViewModel : ViewModel() {

    private val getAllCasesUseCase: GetAllCasesUseCase by inject()
    private val openCaseUseCase: OpenCaseUseCase by inject()

    private val _state = MutableStateFlow(CasesUiState(isLoading = true))
    val state: StateFlow<CasesUiState> = _state.asStateFlow()

    init {
        loadCases()
    }

    fun loadCases() {
        viewModelScope.launch {
            getAllCasesUseCase()
                .onStart { _state.update { it.copy(isLoading = true, error = null) } }
                .catch { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { cases ->
                    _state.update { it.copy(isLoading = false, cases = cases) }
                }
        }
    }

    fun openCase(caseId: String) {
        viewModelScope.launch {
            openCaseUseCase(caseId)
                .onStart { _state.update { it.copy(isLoading = true, error = null) } }
                .catch { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { result ->
                    _state.update { it.copy(isLoading = false, lastOpenResult = result) }
                }
        }
    }

    fun clearLastResult() {
        _state.update { it.copy(lastOpenResult = null) }
    }
}
