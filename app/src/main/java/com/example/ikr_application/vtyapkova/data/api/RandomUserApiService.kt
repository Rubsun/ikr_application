package com.example.ikr_application.vtyapkova.data.api

import retrofit2.http.GET

interface RandomUserApiService {
    @GET("api/")
    suspend fun getRandomUser(): RandomUserResponse
}

data class RandomUserResponse(
    val results: List<RandomUserResult>
)

data class RandomUserResult(
    val name: RandomUserName,
    val picture: RandomUserPicture
)

data class RandomUserName(
    val first: String,
    val last: String
)

data class RandomUserPicture(
    val large: String,
    val medium: String,
    val thumbnail: String
)

