package com.roomstorage.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {

    @Insert
    suspend fun insertCat(cat: CatEntity): Long

    @Query("SELECT * FROM cats ORDER BY created_at DESC")
    fun getAllCatsFlow(): Flow<List<CatEntity>>

    @Query("SELECT * FROM cats WHERE id = :catId")
    suspend fun getCatById(catId: Long): CatEntity?

    @Query("DELETE FROM cats WHERE id = :catId")
    suspend fun deleteCatById(catId: Long)

    @Delete
    suspend fun deleteCat(cat: CatEntity)

    @Query("DELETE FROM cats")
    suspend fun deleteAllCats()
}