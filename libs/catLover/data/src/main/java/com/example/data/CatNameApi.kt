package com.example.data

import retrofit2.http.GET

interface CatNameApi {
    @GET("api/cats/v1")
    suspend fun randomCatName(): List<String>
}
