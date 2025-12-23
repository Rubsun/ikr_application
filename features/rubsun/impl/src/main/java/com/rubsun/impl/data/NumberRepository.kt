package com.rubsun.impl.data

import com.rubsun.impl.data.database.NumberDao
import com.rubsun.impl.data.database.NumberEntity
import com.rubsun.impl.data.models.NumberData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class NumberRepository(
    private val numberDao: NumberDao
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
        val count = numberDao.getCount()
        
        if (count == 0) {
            defaultNumbers.forEach { numberData ->
                val entity = NumberEntity(value = numberData.value, label = numberData.label)
                numberDao.insertNumber(entity)
            }
        }
    }

    val numbers: Flow<List<NumberData>> = numberDao.getAllNumbers().map { entities ->
        if (entities.isEmpty()) {
            defaultNumbers
        } else {
            entities.map { it.toNumberData() }
        }
    }

    suspend fun getRandomNumber(): NumberData = withContext(Dispatchers.IO) {
        val entity = numberDao.getRandomNumber()
        if (entity != null) {
            entity.toNumberData()
        } else {
            defaultNumbers.random()
        }
    }

    suspend fun addNumber(value: Int, label: String) = withContext(Dispatchers.IO) {
        val entity = NumberEntity(value = value, label = label)
        numberDao.insertNumber(entity)
    }

    private fun NumberEntity.toNumberData(): NumberData {
        return NumberData(value = value, label = label)
    }
}

