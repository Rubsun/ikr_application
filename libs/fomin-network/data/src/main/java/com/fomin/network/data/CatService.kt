package com.fomin.network.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface CatService {
    @GET("v1/breeds")
    suspend fun getBreeds(): List<BreedDto>
    
    @GET("v1/images/search")
    suspend fun getBreedImages(
        @Query("breed_ids") breedId: String,
        @Query("limit") limit: Int = 10,
    ): List<ImageDto>
    
    @GET("v1/images/{image_id}")
    suspend fun getImageById(@Path("image_id") imageId: String): ImageDto
}

