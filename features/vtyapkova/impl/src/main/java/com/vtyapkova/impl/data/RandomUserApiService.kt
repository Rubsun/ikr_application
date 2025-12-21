package com.vtyapkova.impl.data

import retrofit2.http.GET

internal interface RandomUserApiService {
    @GET("api/")
    suspend fun getRandomUser(): RandomUserResponse
}

internal data class RandomUserResponse(
    val results: List<RandomUserResult>
)

internal data class RandomUserResult(
    val name: RandomUserName,
    val picture: RandomUserPicture
)

internal data class RandomUserName(
    val first: String,
    val last: String
)

internal data class RandomUserPicture(
    val large: String,
    val medium: String,
    val thumbnail: String
)
