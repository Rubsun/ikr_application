package com.fomin.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.injector.inject
import com.fomin.api.domain.usecases.GetBreedDetailsUseCase
import com.fomin.api.domain.usecases.GetBreedImagesUseCase
import com.fomin.impl.ui.state.BreedDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

internal class BreedDetailViewModel : ViewModel() {

    private val getBreedDetailsUseCase: GetBreedDetailsUseCase by inject()
    private val getBreedImagesUseCase: GetBreedImagesUseCase by inject()

    private val _state = MutableStateFlow(BreedDetailState(isLoading = true))
    val state: StateFlow<BreedDetailState> = _state.asStateFlow()

    fun loadBreed(breedId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val breedResult = getBreedDetailsUseCase(breedId)
            val imagesResult = getBreedImagesUseCase(breedId, limit = 10)

            breedResult
                .onSuccess { breed ->
                    imagesResult
                        .onSuccess { images ->
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    breed = breed,
                                    images = images,
                                    error = null
                                )
                            }
                        }
                        .onFailure { e ->
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    breed = breed,
                                    images = emptyList(),
                                    error = e.toUiMessage()
                                )
                            }
                        }
                }
                .onFailure { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.toUiMessage()
                        )
                    }
                }
        }
    }

    private fun Throwable.toUiMessage(): String {
        return if (this is IOException) {
            "Не удалось загрузить данные. Проверьте интернет-соединение."
        } else {
            message ?: "Не удалось загрузить данные."
        }
    }
}


