package com.dimmension.impl.data.remote

import com.dimmension.impl.data.models.NameRecordDto
import com.dimmension.network.api.RandomUserApi

/**
 * Источник данных для получения имён из сети.
 * Использует абстракцию RandomUserApi без прямых ссылок на внешние библиотеки.
 */
internal class RemoteNameDataSource(
    private val randomUserApi: RandomUserApi
) {
    /**
     * Загружает случайные имена из интернета
     * @param count количество имён для загрузки
     * @return список NameRecordDto
     */
    suspend fun fetchRandomNames(count: Int): Result<List<NameRecordDto>> {
        return randomUserApi.getRandomUsers(count).map { users ->
            users.map { user ->
                NameRecordDto(
                    firstName = user.firstName,
                    lastName = user.lastName
                )
            }
        }
    }

    /**
     * Загружает одно случайное имя из интернета
     */
    suspend fun fetchRandomName(): Result<NameRecordDto> {
        return fetchRandomNames(1).map { it.first() }
    }
}

