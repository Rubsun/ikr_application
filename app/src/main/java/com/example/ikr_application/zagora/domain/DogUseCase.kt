package com.example.ikr_application.zagora.domain

import com.example.ikr_application.zagora.data.Repository


data class DogImageModel(
    val imageUrl: String,
    val title: String = "Dog"
)

class GetDogImageUseCase(private val repository: Repository) {
    suspend fun execute(): DogImageModel {
        val dogImageDto = repository.fetchDogImage()
        return DogImageModel(imageUrl = dogImageDto.message)
    }
}