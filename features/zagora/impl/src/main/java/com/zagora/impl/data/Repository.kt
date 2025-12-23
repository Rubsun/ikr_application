package com.zagora.impl.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class Repository(private val dogApiService: DogApiService) {

    fun fetchDogImageForBreed(breed: String): Flow<DogImageDto> = flow {
        emit(dogApiService.getDogImageForBreed(breed))
    }.flowOn(Dispatchers.IO)

    fun fetchBreeds(): Flow<BreedListDto> = flow {
        emit(dogApiService.getBreeds())
    }.flowOn(Dispatchers.IO)
}
