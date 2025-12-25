package com.rin2396.network.api

import kotlinx.serialization.Serializable

interface CataasCatApiClient {
    suspend fun getRandomCat(): CatImageResponse
}

@Serializable
data class CatImageResponse(
    val url: String,
    val _id: String = ""
)
