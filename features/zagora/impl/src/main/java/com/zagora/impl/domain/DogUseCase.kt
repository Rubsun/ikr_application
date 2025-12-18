package com.zagora.impl.domain

import com.zagora.api.DogImageModel
import com.zagora.impl.data.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetDogBreedsUseCase(private val repository: Repository) {
    fun execute(): Flow<List<String>> = repository.fetchBreeds().map {
        it.message.keys.toList()
    }
}

internal class GetDogImageUseCase(private val repository: Repository) {
    fun execute(breed: String): Flow<DogImageModel> = repository.fetchDogImageForBreed(breed).map {
        DogImageModel(imageUrl = it.message)
    }
}