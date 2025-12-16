package com.example.ikr_application.quovadis.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.quovadis.domain.GetCatUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


class CatViewModel(
    private val getRandomCatUseCase: GetCatUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CatUiState())
    val uiState: StateFlow<CatUiState> = _uiState.asStateFlow()

    fun loadRandomCat(phraseInput: String?) {
        val phrase = phraseInput?.takeIf { it.isNotBlank() }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val cat = withContext(Dispatchers.Default) {
                getRandomCatUseCase.getRandomCat(phrase)
            }

            val imageUrl = withContext(Dispatchers.IO) {
                val encoded = URLEncoder.encode(phrase ?: "Meow", StandardCharsets.UTF_8.toString())
                "https://cataas.com/cat/says/$encoded"
            }

            val catWithImage = cat.copy(imageUrl = imageUrl)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                cats = _uiState.value.cats + catWithImage
            )
        }
    }

    fun onFilterChanged(newFilter: String) {
        _uiState.value = _uiState.value.copy(filter = newFilter)
    }
}
