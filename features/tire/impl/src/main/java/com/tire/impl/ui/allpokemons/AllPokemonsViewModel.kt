package com.tire.impl.ui.allpokemons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.injector.inject
import com.tire.api.domain.models.Pokemon
import com.tire.api.domain.usecases.GetAllPokemonsUseCase
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

internal class AllPokemonsViewModel : ViewModel() {

    private val getAllPokemonsUseCase: GetAllPokemonsUseCase by inject()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val pokemons: Flow<PagingData<Pokemon>> =
        _query
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { q ->
                Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false
                    )
                ) {
                    AllPokemonsPagingSource(
                        getAllPokemonsUseCase = getAllPokemonsUseCase,
                        query = q
                    )
                }.flow
            }
            .cachedIn(viewModelScope)

    fun onQueryChanged(text: String) {
        _query.value = text
    }
}


