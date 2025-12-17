package com.example.ikr_application.zagora.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository {

    private val dogApiService = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DogApiService::class.java)

    fun fetchDogImageForBreed(breed: String): Flow<DogImageDto> = flow {
        emit(dogApiService.getDogImageForBreed(breed))
    }.flowOn(Dispatchers.IO)

    fun fetchBreeds(): Flow<BreedListDto> = flow {
        emit(dogApiService.getBreeds())
    }.flowOn(Dispatchers.IO)
}