package com.zagora.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zagora.api.ZagoraUiState
import com.zagora.impl.data.ZagoraStateHolder
import com.zagora.impl.domain.GetDogBreedsUseCase
import com.zagora.impl.domain.GetDogImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

internal class MyViewModel(
    private val getDogBreedsUseCase: GetDogBreedsUseCase,
    private val getDogImageUseCase: GetDogImageUseCase,
    private val stateHolder: ZagoraStateHolder
) : ViewModel() {

    private val _uiState = MutableStateFlow(ZagoraUiState())
    val uiState: StateFlow<ZagoraUiState> = _uiState.asStateFlow()

    private val placeholder = "Select breed"

    init {
        loadBreeds()
    }

    private fun loadBreeds() {
        getDogBreedsUseCase.execute()
            .onEach { breeds ->
                val breedsWithPlaceholder = listOf(placeholder) + breeds
                val selectedBreed = stateHolder.getSelectedBreed(placeholder)
                _uiState.update { it.copy(breeds = breedsWithPlaceholder, selectedBreed = selectedBreed) }
                if (selectedBreed != placeholder) {
                    loadImageForBreed(selectedBreed)
                }
            }
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }

    fun selectBreed(breed: String) {
        if (breed == _uiState.value.selectedBreed) return

        stateHolder.saveSelectedBreed(breed)
        _uiState.update { it.copy(selectedBreed = breed) }

        if (breed != placeholder) {
            loadImageForBreed(breed)
        } else {
            _uiState.update { it.copy(dogImage = null) }
        }
    }

    fun regenerateImage() {
        _uiState.value.selectedBreed?.let { currentBreed ->
            if (currentBreed != placeholder) {
                loadImageForBreed(currentBreed)
            }
        }
    }

    private fun loadImageForBreed(breed: String) {
        _uiState.update { it.copy(isLoading = true) }
        getDogImageUseCase.execute(breed)
            .onEach { dogImage ->
                _uiState.update { it.copy(dogImage = dogImage, isLoading = false) }
            }
            .catch { e ->
                e.printStackTrace()
                _uiState.update { it.copy(isLoading = false) }
            }
            .launchIn(viewModelScope)
    }
}