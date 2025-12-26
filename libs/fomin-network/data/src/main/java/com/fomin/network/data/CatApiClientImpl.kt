package com.fomin.network.data

import com.example.network.api.RetrofitServiceFactory
import com.fomin.network.api.CatApiClient
import com.fomin.network.api.CatBreed
import com.fomin.network.api.CatImage

private const val BASE_URL = "https://api.thecatapi.com/"

internal class CatApiClientImpl(
    private val retrofitServiceFactory: RetrofitServiceFactory,
) : CatApiClient {

    private val service: CatService by lazy {
        retrofitServiceFactory.create(BASE_URL, CatService::class.java)
    }

    override suspend fun getBreeds(): List<CatBreed> {
        return service.getBreeds().map { it.toDomain() }
    }

    override suspend fun getBreedById(breedId: String): CatBreed? {
        return try {
            // Cat API doesn't have direct endpoint for breed by ID, so we filter from all breeds
            service.getBreeds().firstOrNull { it.id == breedId }?.toDomain()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getBreedImages(breedId: String, limit: Int): List<CatImage> {
        return service.getBreedImages(breedId, limit).map { it.toDomain() }
    }

    override suspend fun getImageById(imageId: String): CatImage? {
        return try {
            service.getImageById(imageId).toDomain()
        } catch (e: Exception) {
            null
        }
    }
}

