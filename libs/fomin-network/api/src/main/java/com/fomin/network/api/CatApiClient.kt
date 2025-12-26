package com.fomin.network.api

interface CatApiClient {
    suspend fun getBreeds(): List<CatBreed>
    
    suspend fun getBreedById(breedId: String): CatBreed?
    
    suspend fun getBreedImages(breedId: String, limit: Int = 10): List<CatImage>
    
    suspend fun getImageById(imageId: String): CatImage?
}


