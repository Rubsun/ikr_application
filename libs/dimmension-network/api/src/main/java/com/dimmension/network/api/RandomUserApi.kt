package com.dimmension.network.api

/**
 * Интерфейс для получения случайных пользователей из API
 */
interface RandomUserApi {
    /**
     * Получает список случайных пользователей
     * @param count количество пользователей
     * @return список имён пользователей (пары firstName, lastName)
     */
    suspend fun getRandomUsers(count: Int): Result<List<RandomUserData>>
}

/**
 * Данные о случайном пользователе
 */
data class RandomUserData(
    val firstName: String,
    val lastName: String
)

