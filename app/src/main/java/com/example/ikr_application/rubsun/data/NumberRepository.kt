package com.example.ikr_application.rubsun.data

import com.example.ikr_application.rubsun.data.models.NumberData

class NumberRepository {
    private val numbers = listOf(
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

    fun getRandomNumber(): NumberData {
        return numbers.random()
    }

    fun getAllNumbers(): List<NumberData> {
        return numbers
    }
}
