package com.vtyapkova.network.api

import retrofit2.http.GET

/**
 * API клиент для получения случайных пользователей.
 * Интерфейс для работы с RandomUser API.
 */
interface RandomUserApiClient {
    @GET("api/")
    suspend fun getRandomUser(): RandomUserResponse
}

/**
 * Ответ от API RandomUser
 */
data class RandomUserResponse(
    val results: List<RandomUserResult>
)

/**
 * Результат с данными пользователя
 */
data class RandomUserResult(
    val name: RandomUserName
)

/**
 * Имя пользователя
 */
data class RandomUserName(
    val first: String,
    val last: String
)

