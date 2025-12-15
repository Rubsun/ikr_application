package com.example.ikr_application.rubsun.data

import com.example.ikr_application.rubsun.data.models.NumberData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class NumberRepository {
    private val _numbers = MutableStateFlow(
        listOf(
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
    )
    val numbers: Flow<List<NumberData>> = _numbers.asStateFlow()

    suspend fun getRandomNumber(): NumberData = withContext(Dispatchers.Default) {
        delay(100) // mock heavy operation
        _numbers.value.random()
    }

    suspend fun getAllNumbers(): List<NumberData> = withContext(Dispatchers.IO) {
        delay(50) // mock load data
        _numbers.value
    }

    suspend fun addNumber(value: Int, label: String) = withContext(Dispatchers.IO) {
        delay(200) // mock save
        val newNumber = NumberData(value = value, label = label)
        _numbers.value = _numbers.value + newNumber
    }
}
