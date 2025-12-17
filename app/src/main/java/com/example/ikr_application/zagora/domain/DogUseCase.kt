package com.example.ikr_application.zagora.domain

import com.example.ikr_application.zagora.data.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class DogImageModel(
    val imageUrl: String,
    val title: String = "Dog"
)

data class ZagoraUiState(
    val breeds: List<String> = emptyList(),
    val selectedBreed: String? = null,
    val dogImage: DogImageModel? = null,
    val isLoading: Boolean = false
)

class GetDogBreedsUseCase(private val repository: Repository) {
    fun execute(): Flow<List<String>> = repository.fetchBreeds().map {
        it.message.keys.toList()
    }
}

class GetDogImageUseCase(private val repository: Repository) {
    fun execute(breed: String): Flow<DogImageModel> = repository.fetchDogImageForBreed(breed).map {
        DogImageModel(imageUrl = it.message)
    }
}