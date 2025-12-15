package com.example.ikr_application.zagora.data

import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds/list/all")
    suspend fun getBreeds(): BreedListDto

    @GET("breed/{breed}/images/random")
    suspend fun getDogImageForBreed(@Path("breed") breed: String): DogImageDto
}

data class BreedListDto(
    val message: Map<String, List<String>>,
    val status: String
)

data class DogImageDto(
    val message: String,
    val status: String
)
