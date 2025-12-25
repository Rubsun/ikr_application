package com.rubsun.storage.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface RoomNumberDao {
    @Query("SELECT * FROM numbers")
    fun getAllNumbers(): Flow<List<RoomNumberEntity>>

    @Query("SELECT * FROM numbers ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomNumber(): RoomNumberEntity?

    @Query("SELECT COUNT(*) FROM numbers")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumber(number: RoomNumberEntity): Long

    @Query("DELETE FROM numbers WHERE id = :id")
    suspend fun deleteNumber(id: Long)

    @Query("DELETE FROM numbers")
    suspend fun deleteAllNumbers()
}


