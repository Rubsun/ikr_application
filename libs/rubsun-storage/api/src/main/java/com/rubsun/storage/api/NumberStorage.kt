package com.rubsun.storage.api

import com.rubsun.storage.api.models.NumberEntity
import kotlinx.coroutines.flow.Flow

interface NumberStorage {
    fun getAllNumbers(): Flow<List<NumberEntity>>

    suspend fun getRandomNumber(): NumberEntity?

    suspend fun getCount(): Int

    suspend fun insertNumber(number: NumberEntity): Long

    suspend fun deleteNumber(id: Long)

    suspend fun deleteAllNumbers()
}


