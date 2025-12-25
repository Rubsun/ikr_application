package com.rubsun.impl.data

import com.rubsun.impl.data.models.NumberData
import com.rubsun.network.api.NumberApiClient
import com.rubsun.storage.api.NumberStorage
import com.rubsun.storage.api.models.NumberEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal class NumberRepository(
    private val numberStorage: NumberStorage,
    private val numberApiClient: NumberApiClient
) {
    private val defaultNumbers = listOf(
        NumberData(value = 1, label = "Один"),
        NumberData(value = 2, label = "Два"),
        NumberData(value = 3, label = "Три"),
        NumberData(value = 4, label = "Четыре"),
        NumberData(value = 5, label = "Пять"),
        NumberData(value = 6, label = "Шесть"),
        NumberData(value = 7, label = "Семь"),
        NumberData(value = 8, label = "Восемь"),
        NumberData(value = 9, label = "Девять"),
        NumberData(value = 10, label = "Десять"),
    )

    suspend fun initializeDefaultNumbers() = withContext(Dispatchers.IO) {
        val count = numberStorage.getCount()
        
        if (count == 0) {
            defaultNumbers.forEach { numberData ->
                val entity = NumberEntity(value = numberData.value, label = numberData.label)
                numberStorage.insertNumber(entity)
            }
        }
    }

    val numbers: Flow<List<NumberData>> = numberStorage.getAllNumbers().map { entities ->
        val dbNumbers = entities.map { it.toNumberData() }
        val defaultValues = defaultNumbers.map { it.value }.toSet()
        val dbValues = dbNumbers.map { it.value }.toSet()
        val dbNumbersMap = dbNumbers.associateBy { it.value }
        val defaultsWithDbData = defaultNumbers.map { default ->
            dbNumbersMap[default.value] ?: default
        }
        val additionalNumbers = dbNumbers.filter { it.value !in defaultValues }
        defaultsWithDbData + additionalNumbers
    }

    suspend fun getRandomNumber(): NumberData = withContext(Dispatchers.IO) {
        try {
            val post = numberApiClient.getRandomPost()
            if (post != null) {
                val label = "Пост #${post.id}: ${post.displayTitle}"
                NumberData(value = post.id, label = label)
            } else {
                val entity = numberStorage.getRandomNumber()
                if (entity != null) {
                    entity.toNumberData()
                } else {
                    defaultNumbers.random()
                }
            }
        } catch (e: Exception) {
            val entity = numberStorage.getRandomNumber()
            if (entity != null) {
                entity.toNumberData()
            } else {
                defaultNumbers.random()
            }
        }
    }
    
    suspend fun fetchNumberFactFromApi(number: Int): NumberData = withContext(Dispatchers.IO) {
        try {
            val postId = number.coerceIn(1, 100)
            val post = numberApiClient.getPostById(postId)
            if (post != null) {
                val shortBody = if (post.body.length > 100) {
                    post.body.take(100) + "..."
                } else {
                    post.body
                }
                val label = "Пост #${post.id}: ${post.displayTitle}\n$shortBody"
                NumberData(value = post.id, label = label)
            } else {
                NumberData(value = postId, label = "Пост с ID $postId не найден. Используйте число от 1 до 100.")
            }
        } catch (e: SocketTimeoutException) {
            NumberData(value = number, label = "Превышено время ожидания")
        } catch (e: UnknownHostException) {
            NumberData(value = number, label = "Нет подключения к интернету")
        } catch (e: ConnectException) {
            NumberData(value = number, label = "Не удалось подключиться к серверу")
        } catch (e: Exception) {
            val errorMessage = e.message ?: "Неизвестная ошибка"
            val errorClass = e.javaClass.simpleName
            when {
                errorMessage.contains("404") || errorMessage.contains("Not Found") -> {
                    val postId = number.coerceIn(1, 100)
                    NumberData(value = number, label = "Пост с ID $postId не найден")
                }
                errorMessage.contains("timeout", ignoreCase = true) || errorClass.contains("Timeout") -> {
                    NumberData(value = number, label = "Превышено время ожидания")
                }
                errorMessage.contains("500", ignoreCase = true) || errorClass.contains("ServerError") -> {
                    NumberData(value = number, label = "Ошибка сервера. Попробуйте позже.")
                }
                else -> {
                    NumberData(value = number, label = "Ошибка загрузки: $errorClass")
                }
            }
        }
    }

    suspend fun addNumber(value: Int, label: String) = withContext(Dispatchers.IO) {
        val entity = NumberEntity(value = value, label = label)
        numberStorage.insertNumber(entity)
    }

    suspend fun clearAllNumbers() = withContext(Dispatchers.IO) {
        numberStorage.deleteAllNumbers()
        initializeDefaultNumbers()
    }

    private fun NumberEntity.toNumberData(): NumberData {
        return NumberData(value = value, label = label)
    }
}

