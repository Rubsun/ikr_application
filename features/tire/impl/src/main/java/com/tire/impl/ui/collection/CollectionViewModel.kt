package com.tire.impl.ui.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.injector.inject
import com.tire.api.domain.usecases.GetCollectionStatsUseCase
import com.tire.api.domain.usecases.GetMyCollectionUseCase
import com.tire.impl.ui.states.CollectionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class CollectionViewModel : ViewModel() {

    private val getMyCollectionUseCase: GetMyCollectionUseCase by inject()
    private val getCollectionStatsUseCase: GetCollectionStatsUseCase by inject()

    private val _state = MutableStateFlow(CollectionUiState(isLoading = true))
    val state: StateFlow<CollectionUiState> = _state.asStateFlow()

    init {
        loadCollection()
    }

    fun loadCollection() {
        viewModelScope.launch {
            combine(
                getMyCollectionUseCase(),
                getCollectionStatsUseCase()
            ) { pokemons, stats ->
                pokemons to stats
            }
                .onStart { _state.update { it.copy(isLoading = true, error = null) } }
                .catch { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { (pokemons, stats) ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            pokemons = pokemons,
                            stats = stats
                        )
                    }
                }
        }
    }
}
