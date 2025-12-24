package com.dimmension.network.data.randomuser

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API сервис для получения случайных пользователей
 * Использует https://randomuser.me/api/
 */
internal interface RandomUserApiService {
    @GET("api/")
    suspend fun getRandomUsers(
        @Query("results") count: Int = 5,
        @Query("nat") nationality: String = "ru",
        @Query("inc") include: String = "name"
    ): RandomUserResponse
}

