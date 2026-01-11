package com.tire.impl.ui.cases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.injector.inject
import com.tire.api.domain.usecases.GetAllCasesUseCase
import com.tire.api.domain.usecases.OpenCaseUseCase
import com.tire.impl.ui.states.CasesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
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
