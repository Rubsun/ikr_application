package com.example.ikr_application.zagora.data

import retrofit2.http.GET

interface DogApiService {
    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): DogImageDto
}

data class DogImageDto(
    val message: String,
    val status: String
)