package com.fomin.impl.data

import com.fomin.api.domain.models.CatBreed
import com.fomin.api.domain.models.CatImage
import com.fomin.network.api.CatApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CatRepository(
    private val apiClient: CatApiClient,
) {
    suspend fun getBreeds(): List<CatBreed> {
        return withContext(Dispatchers.IO) {
            apiClient.getBreeds().map { networkBreed ->
                CatBreed(
                    id = networkBreed.id,
                    name = networkBreed.name,
                    description = networkBreed.description,
                    temperament = networkBreed.temperament,
                    origin = networkBreed.origin,
                    lifeSpan = networkBreed.lifeSpan,
                    weight = networkBreed.weight?.let { weight ->
                        com.fomin.api.domain.models.CatWeight(
                            imperial = weight.imperial,
                            metric = weight.metric,
                        )
                    },
                    imageUrl = networkBreed.image?.url,
                )
            }
        }
    }

    suspend fun getBreedById(breedId: String): CatBreed? {
        return withContext(Dispatchers.IO) {
            apiClient.getBreedById(breedId)?.let { networkBreed ->
                CatBreed(
                    id = networkBreed.id,
                    name = networkBreed.name,
                    description = networkBreed.description,
                    temperament = networkBreed.temperament,
                    origin = networkBreed.origin,
                    lifeSpan = networkBreed.lifeSpan,
                    weight = networkBreed.weight?.let { weight ->
                        com.fomin.api.domain.models.CatWeight(
                            imperial = weight.imperial,
                            metric = weight.metric,
                        )
                    },
                    imageUrl = networkBreed.image?.url,
                )
            }
        }
    }

    suspend fun getBreedImages(breedId: String, limit: Int = 10): List<CatImage> {
        return withContext(Dispatchers.IO) {
            apiClient.getBreedImages(breedId, limit).map { networkImage ->
                CatImage(
                    id = networkImage.id,
                    url = networkImage.url,
                    width = networkImage.width,
                    height = networkImage.height,
                )
            }
        }
    }
}


