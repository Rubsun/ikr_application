package com.rubsun.impl.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface NumberDao {
    @Query("SELECT * FROM numbers")
    fun getAllNumbers(): Flow<List<NumberEntity>>

    @Query("SELECT * FROM numbers ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomNumber(): NumberEntity?

    @Query("SELECT COUNT(*) FROM numbers")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumber(number: NumberEntity): Long

    @Query("DELETE FROM numbers WHERE id = :id")
    suspend fun deleteNumber(id: Long)
}

