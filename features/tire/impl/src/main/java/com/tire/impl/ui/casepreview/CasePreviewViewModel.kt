package com.tire.impl.ui.casepreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.injector.inject
import com.tire.impl.ui.states.CasePreviewUiState
import com.tire.api.domain.usecases.GetCasePreviewUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


internal class CasePreviewViewModel(
    private val caseId: String
) : ViewModel() {

    private val getCasePreviewUseCase: GetCasePreviewUseCase by inject()

    private val _state = MutableStateFlow(CasePreviewUiState(isLoading = true))
    val state: StateFlow<CasePreviewUiState> = _state.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            getCasePreviewUseCase(caseId)
                .onStart { _state.update { it.copy(isLoading = true, error = null) } }
                .catch { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { preview ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            pokemons = preview.pokemons,
                            caseName = preview.caseName,
                            caseInfo = preview.caseInfo,
                            caseImageUrl = preview.caseImageUrl
                        )
                    }
                }

        }
    }

}
