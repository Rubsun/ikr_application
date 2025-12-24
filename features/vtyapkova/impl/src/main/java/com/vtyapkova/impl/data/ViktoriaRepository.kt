package com.vtyapkova.impl.data

import com.vtyapkova.impl.data.models.ViktoriaData
import com.vtyapkova.impl.data.storage.ViktoriaStorage
import com.vtyapkova.network.api.RandomUserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class ViktoriaRepository(
    private val storage: ViktoriaStorage,
    private val apiClient: RandomUserApiClient
) {
    private val _allData = MutableStateFlow<List<ViktoriaData>>(emptyList())

    suspend fun initialize() {
        storage.loadData()
        // Получаем начальные данные
        val initialData = storage.getCurrentData()
        _allData.value = initialData
    }

    fun getAllData(): Flow<List<ViktoriaData>> = _allData.asStateFlow()

    fun addViktoria(firstName: String, lastName: String): Flow<Unit> = flow {
        kotlinx.coroutines.delay(200)
        val fullName = "$firstName $lastName"
        val initials = "${firstName.first()}.${lastName.first()}."
        val newData = ViktoriaData(
            firstName = firstName,
            lastName = lastName,
            fullName = fullName,
            initials = initials
        )
        val currentData = _allData.value + newData
        _allData.value = currentData
        storage.saveData(currentData)
        emit(Unit)
    }.flowOn(Dispatchers.Default)

    fun addViktoriaFromApi(): Flow<Unit> = flow {
        val response = apiClient.getRandomUser()
        val user = response.results.first()
        val newData = ViktoriaData(
            firstName = user.name.first,
            lastName = user.name.last,
            fullName = "${user.name.first} ${user.name.last}",
            initials = "${user.name.first.first()}.${user.name.last.first()}."
        )
        val currentData = _allData.value + newData
        _allData.value = currentData
        storage.saveData(currentData)
        emit(Unit)
    }.flowOn(Dispatchers.IO)

    fun filterData(query: String): Flow<List<ViktoriaData>> = flow {
        kotlinx.coroutines.delay(150)
        val result = if (query.isBlank()) {
            _allData.value
        } else {
            val lowerQuery = query.lowercase()
            _allData.value.filter {
                it.firstName.lowercase().contains(lowerQuery) ||
                it.lastName.lowercase().contains(lowerQuery) ||
                it.fullName.lowercase().contains(lowerQuery)
            }
        }
        emit(result)
    }.flowOn(Dispatchers.Default)

    fun getRandomViktoria(): Flow<ViktoriaData> = flow {
        kotlinx.coroutines.delay(100)
        val data = _allData.value
        if (data.isNotEmpty()) {
            emit(data.random())
        } else {
            // Генерируем случайное имя если данных нет
            val firstNames = listOf("James", "John", "Robert", "Michael", "William")
            val lastNames = listOf("Smith", "Johnson", "Williams", "Brown", "Jones")
            val firstName = firstNames.random()
            val lastName = lastNames.random()
            emit(
                ViktoriaData(
                    firstName = firstName,
                    lastName = lastName,
                    fullName = "$firstName $lastName",
                    initials = "${firstName.first()}.${lastName.first()}."
                )
            )
        }
    }.flowOn(Dispatchers.Default)
}

