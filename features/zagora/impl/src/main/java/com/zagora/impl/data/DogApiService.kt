package com.zagora.impl.data

import retrofit2.http.GET
import retrofit2.http.Path

internal interface DogApiService {
    @GET("breeds/list/all")
    suspend fun getBreeds(): BreedListDto

    @GET("breed/{breed}/images/random")
    suspend fun getDogImageForBreed(@Path("breed") breed: String): DogImageDto
}

internal data class BreedListDto(
    val message: Map<String, List<String>>,
    val status: String
)

internal data class DogImageDto(
    val message: String,
    val status: String
)
