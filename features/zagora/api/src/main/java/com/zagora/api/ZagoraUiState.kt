package com.zagora.api

data class ZagoraUiState(
    val breeds: List<String> = emptyList(),
    val selectedBreed: String? = null,
    val dogImage: DogImageModel? = null,
    val isLoading: Boolean = false
)
