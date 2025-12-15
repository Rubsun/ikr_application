package com.example.ikr_application.zagora.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.zagora.data.Repository
import com.example.ikr_application.zagora.domain.GetDogBreedsUseCase
import com.example.ikr_application.zagora.domain.GetDogImageUseCase
import com.example.ikr_application.zagora.domain.ZagoraUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class MyViewModel : ViewModel() {

    private val getDogBreedsUseCase: GetDogBreedsUseCase
    private val getDogImageUseCase: GetDogImageUseCase

    private val _uiState = MutableStateFlow(ZagoraUiState())
    val uiState: StateFlow<ZagoraUiState> = _uiState.asStateFlow()

    private val placeholder = "Select breed"

    init {
        val repository = Repository()
        getDogBreedsUseCase = GetDogBreedsUseCase(repository)
        getDogImageUseCase = GetDogImageUseCase(repository)
        loadBreeds()
    }

    private fun loadBreeds() {
        getDogBreedsUseCase.execute()
            .onEach { breeds ->
                val breedsWithPlaceholder = listOf(placeholder) + breeds
                _uiState.update { it.copy(breeds = breedsWithPlaceholder, selectedBreed = placeholder) }
            }
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }

    fun selectBreed(breed: String) {
        if (breed == _uiState.value.selectedBreed) return

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